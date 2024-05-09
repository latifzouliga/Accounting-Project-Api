package com.cydeo.service.impl;

import com.cydeo.config.KeycloakProperties;
import com.cydeo.dto.UserDto;
import com.cydeo.service.KeycloakService;


import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
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


        try (Keycloak keycloak = getKeycloakInstance()) {
            UserRepresentation keycloakUser = createUserRepresentation(userDto);
            RealmResource realmResource = keycloak.realm(keycloakProperties.getRealm());
            UsersResource usersResource = realmResource.users();

            // Create Keycloak user
            Response result = usersResource.create(keycloakUser);
            String userId = getCreatedId(result);

            ClientRepresentation appClient = realmResource.clients()
                    .findByClientId(keycloakProperties.getClientId())
                    .get(0);

            // Get the role from user
            RoleRepresentation userClientRole = realmResource.clients()
                    .get(appClient.getId())
                    .roles()
                    .get(userDto.getRole().getDescription())
                    .toRepresentation();

            // Assign the role to the user
            realmResource.users()
                    .get(userId)
                    .roles()
                    .clientLevel(appClient.getId())
                    .add(List.of(userClientRole));

            return result;
        } catch (Exception e) {
            // Handle exceptions properly
            e.printStackTrace();
            throw new RuntimeException("Failed to create user", e);
        }
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
        credential.setValue(userDto.getPassword());

        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setUsername(userDto.getUsername());
        keycloakUser.setFirstName(userDto.getFirstname());
        keycloakUser.setLastName(userDto.getLastname());
        keycloakUser.setEmail(userDto.getUsername());
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
