package com.zouliga;

;
import com.zouliga.dto.RoleDto;
import com.zouliga.dto.UserDto;
import com.zouliga.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

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
                    .role(RoleDto.builder().description("Root User").build())
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
