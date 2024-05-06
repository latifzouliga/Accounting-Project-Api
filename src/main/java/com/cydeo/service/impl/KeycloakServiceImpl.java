package com.cydeo.service.impl;
import com.cydeo.config.KeycloakProperties;
import com.cydeo.dto.UserDto;
import com.cydeo.service.KeycloakService;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;


import java.util.List;

import static org.keycloak.admin.client.CreatedResponseUtil.getCreatedId;

@Service
public class KeycloakServiceImpl implements KeycloakService {

    private final KeycloakProperties keycloakProperties;

    public KeycloakServiceImpl(KeycloakProperties keycloakProperties) {
        this.keycloakProperties = keycloakProperties;
    }

    @Override
    public Response userCreate(UserDto userDto) {

        UserRepresentation keycloakUser = createUserRepresentation(userDto);

        // open instance
        Keycloak keycloak = getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(keycloakProperties.getRealm());
        UsersResource usersResource = realmResource.users();

        // Create Keycloak user
        Response result = usersResource.create(keycloakUser);
        String userId = getCreatedId(result);

        ClientRepresentation appClient = realmResource.clients()
                .findByClientId(keycloakProperties.getClientId()).get(0);

        // getting the role from user, matching that role in keycloak and assigning it to user
        RoleRepresentation userClientRole = realmResource.clients().get(appClient.getId())
                .roles().get(userDto.role().description()).toRepresentation();

        realmResource.users().get(userId).roles().clientLevel(appClient.getId())
                .add(List.of(userClientRole));

        keycloak.close();
        return result;
    }

    @Override
    public void delete(String userName) {
        Keycloak keycloak = getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(keycloakProperties.getRealm());
        UsersResource usersResource = realmResource.users();

        // find the user using username
        List<UserRepresentation> userRepresentations = usersResource.search(userName);
        String uid = userRepresentations.get(0).getId();

        usersResource.delete(uid);
        keycloak.close();
    }

    private UserRepresentation createUserRepresentation(UserDto userDto) {
        // filling user info in keycloak application
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setTemporary(false);
        credential.setValue(userDto.password());

        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setUsername(userDto.username());
        keycloakUser.setFirstName(userDto.firstname());
        keycloakUser.setLastName(userDto.lastname());
        keycloakUser.setEmail(userDto.username());
        keycloakUser.setCredentials(List.of(credential));
        keycloakUser.setEmailVerified(true);
        keycloakUser.setEnabled(true);

        return keycloakUser;
    }


    private Keycloak getKeycloakInstance() {
        return Keycloak.getInstance(keycloakProperties.getAuthServerUrl(),
                keycloakProperties.getMasterRealm(), keycloakProperties.getMasterUser(),
                keycloakProperties.getMasterUserPswd(), keycloakProperties.getMasterClient());
    }
}
