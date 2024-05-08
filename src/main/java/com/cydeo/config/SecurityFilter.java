package com.cydeo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class OAuth2ResourceServerSecurityConfiguration {
    @Value("${keycloak.resource}")
    private String keycloakClientName;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((authorize) -> {
                    authorize
                            .anyRequest().authenticated();
                })
                .oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer -> httpSecurityOAuth2ResourceServerConfigurer
                        .jwt(jwtConfigurer -> {
                            jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter());
                        })
                );

        return httpSecurity.build();
    }
    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());

        return jwtAuthenticationConverter;
    }

    private class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
        private Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        @Override
        public Collection<GrantedAuthority> convert(Jwt jwt) {
            final Map<String, Object> resourceAccess = (Map<String, Object>) jwt.getClaims().get("resource_access");

            if (resourceAccess != null) {
                final Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get(OAuth2ResourceServerSecurityConfiguration.this.keycloakClientName);

                if (clientAccess != null) {
                    grantedAuthorities = ((List<String>) clientAccess.get("roles")).stream()
                            .map(roleName -> "ROLE_" + roleName) // Prefix to map to a Spring Security "role"
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                }
            }

            return grantedAuthorities;
        }
    }
}