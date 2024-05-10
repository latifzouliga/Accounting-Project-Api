package com.cydeo;

;
import com.cydeo.service.KeycloakService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Objects;

@SpringBootApplication
public class AccountingProjectApiApplication {

    private final KeycloakService keycloakService;

    public AccountingProjectApiApplication(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AccountingProjectApiApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public CommandLineRunner commandLineRunner() {
//
//        return args -> {
//
//            keycloakService.deleteAllOrphanedUsers(); //delete orphaned users in keycloak database
//
//            String admin = "admin@bluetech.com";
//            String root = "root@cydeo.com";
//            String password = "Abc1";
//
//
//            System.out.println("\n\n\n=====================================================\n\t\tAdmin Token:\n");
//            System.out.println(getToken(admin, password));
//            System.out.println("\n=====================================================\n\t\tRoot Token");
//            System.out.println(getToken(root, password));
//        };
//
//    }

//    private String getToken(String username, String password) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>() {{
//            add("grant_type", "password");
//            add("client_id", "Accounting-Project-Api");
//            add("client_secret", "aC6w0mwERcfbT7iy0bYO76kjQFldFWE9");
//            add("username", username);
//            add("password", password);
//            add("scope", "openid");
//        }};
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpEntity<MultiValueMap<String, String>> entry = new HttpEntity<>(map, headers);
//
//        ResponseEntity<HashMap> response = restTemplate.exchange(
//                "http://localhost:8080/realms/zouliga-dev/protocol/openid-connect/token",
//                HttpMethod.POST,
//                entry,
//                HashMap.class
//        );
//        return (String) Objects.requireNonNull(response.getBody()).get("access_token");
//    }

}
