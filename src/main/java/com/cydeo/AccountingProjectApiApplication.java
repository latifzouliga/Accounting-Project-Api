package com.cydeo;

;
import com.cydeo.controller.Utility;
import com.cydeo.dto.RoleDto;
import com.cydeo.dto.UserDto;
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
import java.util.List;
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


            // users for keycloak server
            UserDto rootUser = UserDto.builder()
                    .username("root@cydeo.com")
                    .password("Abc1")
                    .firstname("Robert")
                    .lastname("Martin")
                    .role(RoleDto.builder().description("Root").build())
                    .build();

            UserDto admin = UserDto.builder()
                    .username("admin@greentech.com")
                    .password("Abc1")
                    .firstname("Mary")
                    .lastname("Grant")
                    .role(RoleDto.builder().description("Admin").build())
                    .build();

            UserDto manager = UserDto.builder()
                    .username("manager@greentech.com")
                    .password("Abc1")
                    .firstname("Robert")
                    .lastname("Noah")
                    .role(RoleDto.builder().description("Manager").build())
                    .build();

            List<UserDto> userDtoList = List.of(rootUser, admin, manager);


            keycloakService.deleteAllOrphanedUsers();
            userDtoList.forEach(keycloakService::userCreate);


        };

    }

}
