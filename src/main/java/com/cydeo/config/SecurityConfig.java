package com.cydeo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;
    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .anyRequest()
                                .authenticated())

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}

//                .antMatchers("/companies/list").hasAuthority("Root")
//                .antMatchers("/companies/create").hasAuthority("Root")
//                .antMatchers("/users/list").hasAuthority("Root")
//                .antMatchers("/users/list").hasAuthority("Admin")
//                .antMatchers("/users/create").hasAuthority("Root")
//                .antMatchers("/users/create").hasAuthority("Admin")
//                .antMatchers("/clientVendors/list").hasAuthority("Admin")
//                .antMatchers("/clientVendors/create").hasAuthority("Admin")
//                .antMatchers("/categories/list").hasAuthority("Admin")
//                .antMatchers("/categories/create").hasAuthority("Admin")
//                .antMatchers("/products/list").hasAuthority("Admin")
//                .antMatchers("/products/create").hasAuthority("Admin")
//                .antMatchers("/purchaseInvoices/list").hasAuthority("Admin")
//                .antMatchers("/purchaseInvoices/print/1").hasAuthority("Admin")
//                .antMatchers("/purchaseInvoices/create").hasAuthority("Admin")
//                .antMatchers("/salesInvoices/list").hasAuthority("Admin")
//                .antMatchers("/salesInvoices/create").hasAuthority("Admin")
//                .antMatchers("/reports/profitLossData").hasAuthority("Admin")
//                .antMatchers("/reports/stockData").hasAuthority("Admin")
//                .antMatchers("/dashboard").hasAuthority("Manager")
//                .antMatchers("/dashboard").hasAuthority("Employee")
//                .antMatchers("/purchaseInvoices/list").hasAuthority("Manager")
//                .antMatchers("/salesInvoices/list").hasAuthority("Manager")