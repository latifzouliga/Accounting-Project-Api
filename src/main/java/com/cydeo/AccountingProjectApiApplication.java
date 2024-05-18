package com.cydeo;

;
import com.cydeo.controller.Utility;
import com.cydeo.entity.common.ApplicationAudiAware;
import com.cydeo.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Objects;

import static com.cydeo.controller.Utility.getToken;

@RequiredArgsConstructor
@SpringBootApplication
public class AccountingProjectApiApplication {

    private final KeycloakService keycloakService;


    public static void main(String[] args) {
        SpringApplication.run(AccountingProjectApiApplication.class, args);
    }





    @Bean
    public CommandLineRunner commandLineRunner() {

        return args -> {

//            keycloakService.deleteAllOrphanedUsers(); //delete orphaned users in keycloak database

//            System.out.println("\n\n\n=====================================================\n\t\tAdmin Token:\n");
//            System.out.println(getToken(admin, password));
//            System.out.println("\n=====================================================\n\t\tRoot Token");
//            System.out.println(getToken(root, password));
        };

    }

}
