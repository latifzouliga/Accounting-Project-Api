package com.cydeo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityFilter {

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers(HttpMethod.GET, "/api/admin").hasRole("admin")
//                                .requestMatchers(HttpMethod.GET, "/api/user").hasRole("user")
//                                .anyRequest().authenticated()
//                )
//                .oauth2ResourceServer(oauth2ResourceServer ->
//                        oauth2ResourceServer.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
//                )
//                .sessionManagement(sessionManagement ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                );
//
//        return http.build();

        http.authorizeHttpRequests()
                .requestMatchers("/api/me").hasRole("admin")
                .requestMatchers( "/api/admin").hasRole("admin")
//                .requestMatchers(HttpMethod.POST, "/api/admin").hasRole("admin")
                .requestMatchers(HttpMethod.GET, "/api/user").hasRole("user")
                .anyRequest().authenticated();

        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthConverter);

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

}