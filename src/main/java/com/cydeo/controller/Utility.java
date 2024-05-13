package com.cydeo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Objects;

public class Utility {

    public static String USERNAME;
    public static final String PASSWORD = "Abc1";


    public static String getRootToken() {
        USERNAME = "root@cydeo.com";
        return getToken(USERNAME, PASSWORD);
    }

    public static String getRootToken(String username,String password) {
        return getToken(username,password);
    }

    public static String getAdminToken() {
        USERNAME = "admin@bluetech.com";
        return getToken(USERNAME, PASSWORD);
    }
    public static String getAdminToken(String username,String password) {
        return getToken(username,password);
    }

    public static String getManagerToken() {
        USERNAME = "manager@bluetech.com";
        return getToken(USERNAME, PASSWORD);
    }
    public static String getManagerToken(String username,String password) {
        return getToken(username,password);
    }

    public static String getEmployeeToken() {
        USERNAME = "employee@bluetech.com";
        return getToken(USERNAME, PASSWORD);
    }
    public static String getEmployeeToken(String username,String password) {
        return getToken(username,password);
    }


    public static String getToken(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>() {{
            add("grant_type", "password");
            add("client_id", "Accounting-Project-Api");
            add("client_secret", "aC6w0mwERcfbT7iy0bYO76kjQFldFWE9");
            add("username", username);
            add("password", password);
            add("scope", "openid");
        }};

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<MultiValueMap<String, String>> entry = new HttpEntity<>(map, headers);

        ResponseEntity<HashMap> response = restTemplate.exchange(
                "http://localhost:8080/realms/zouliga-dev/protocol/openid-connect/token",
                HttpMethod.POST,
                entry,
                HashMap.class
        );
        return (String) Objects.requireNonNull(response.getBody()).get("access_token");
    }

    static String toJsonString(Object object) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}