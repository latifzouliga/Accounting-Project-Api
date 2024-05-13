package com.cydeo.service.impl;

import com.cydeo.config.KeycloakProperties;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;
import com.cydeo.repository.UserRepository;
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
    private final UserRepository userRepository;

    public KeycloakServiceImpl(KeycloakProperties keycloakProperties,
                               UserRepository userRepository) {
        this.keycloakProperties = keycloakProperties;
        this.userRepository = userRepository;
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
        return Keycloak.getInstance(
                keycloakProperties.getAuthServerUrl(),
                keycloakProperties.getMasterRealm(),
                keycloakProperties.getMasterUser(),
                keycloakProperties.getMasterUserPswd(),
                keycloakProperties.getMasterClient());
    }


    /*
        when we create a user from this application, it will create a user in the application database and in keycloak
        database, but when the application goes off the users in application database will be deleted automatically
        and the users in keycloak will still remain
        With this issue the keycloak database will be cluttered with orphaned users and this deleteAll() method will
        always check both app database and keycloak database and when there is orphaned users it will delete the at
        the application start up
     */
    @Override
    public void deleteAllOrphanedUsers() {

        List<String> userList = userRepository.findAll().stream().map(User::getUsername).toList();
        RealmResource realmResource = getKeycloakInstance().realm(keycloakProperties.getRealm());
        List<UserRepresentation> keycloakUsers = realmResource.users().list();

        for (UserRepresentation each : keycloakUsers) {
            if (!userList.contains(each.getUsername())) {
                delete(each.getUsername());
            }
        }
    }


}
