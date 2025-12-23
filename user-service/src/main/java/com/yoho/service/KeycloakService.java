package com.yoho.service;

import com.yoho.payload.response.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class KeycloakService {

    private static final String KEYCLOAK_BASE_URL = "http://localhost:8080";
    private static final String KEYCLOAK_ADMIN_API = KEYCLOAK_BASE_URL+"/admin/realms/master/users";

    private static final String TOKEN_URL = KEYCLOAK_BASE_URL+"/realms/master/protocol/openid-connect/token";

    private static final String CLIENT_ID = "salon-booking-client";
    private static final String CLIENT_SECRET = "wYVAcCRwPiOP3eUDWiX9UPFp1kkvUUnQ";
    private static final String GRANT_TYPE = "password";
    private static final String scope = "openid profile email";
    private static final String username = "zosh";
    private static final String password = "admin";
    private static final String clientId = "91173930-07b9-43c3-8199-a1078cdc01a4";

    private final RestTemplate restTemplate;

    public void createUser(SignupDTO signupDTO)throws Exception{

        String ACCESS_TOKEN = getAdminAccessToken(username, password, GRANT_TYPE, null).getAccessToken();

        Credential credential = new Credential();
        credential.setTemporary(false);
        credential.setType("password");
        credential.setValue(signupDTO.getPassword());

        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(signupDTO.getUsername());
        userRequest.setEmail(signupDTO.getEmail());
        userRequest.setEnabled(true);
        userRequest.setFirstName(signupDTO.getFullName());
        userRequest.getCredentials().add(credential);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(ACCESS_TOKEN);

        HttpEntity<UserRequest> requestHttpEntity = new HttpEntity<>(userRequest,httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                KEYCLOAK_ADMIN_API,
                HttpMethod.POST,
                requestHttpEntity,
                String.class
        );

        if(response.getStatusCode() == HttpStatus.CREATED){
            System.out.println("User created successfully ");

            KeyCloakUserDTO user = fetchFirstUserByUsername(signupDTO.getUsername(),ACCESS_TOKEN);

            KeyCloakRole role = getRoleByName(clientId, ACCESS_TOKEN, signupDTO.getRole().toString());

            List<KeyCloakRole> roles = new ArrayList<>();
            roles.add(role);

            assignRoleToUser(user.getId(),clientId, roles, ACCESS_TOKEN);
        }else{
            System.out.println("User creation failed");
            throw new Exception(response.getBody());
        }

    }

    public TokenResponse getAdminAccessToken(String username, String password, String grandType, String refreshToken) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        requestBody.add("grant_type", grandType);
        requestBody.add("username", username);
        requestBody.add("password", password);
        requestBody.add("refresh_token", refreshToken);
        requestBody.add("client_id", CLIENT_ID);
        requestBody.add("client_secret", CLIENT_SECRET);
        requestBody.add("scope", scope);


        HttpEntity<MultiValueMap<String, String>> requestHttpEntity = new HttpEntity<>(requestBody,httpHeaders);


        try{
            ResponseEntity<TokenResponse> response = restTemplate.exchange(
                    TOKEN_URL,
                    HttpMethod.POST,
                    requestHttpEntity,
                    TokenResponse.class
            );
            return response.getBody();
        }catch (Exception e){
            throw new Exception("Failed to obtain access token "+e.getMessage());
        }

    }

    public KeyCloakRole getRoleByName(String clientId, String token, String role) {
        String url=KEYCLOAK_BASE_URL+"/admin/realms/master/clients/"+clientId+"/roles/"+role;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer "+ token);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<Void> requestHttpEntity = new HttpEntity<>(httpHeaders);


        ResponseEntity<KeyCloakRole> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestHttpEntity,
                KeyCloakRole.class
        );

            return response.getBody();

    }

    public KeyCloakUserDTO fetchFirstUserByUsername(String username, String token) throws Exception {
        String url=KEYCLOAK_BASE_URL+"/admin/realms/master/users?username="+username;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<String> requestHttpEntity = new HttpEntity<>(httpHeaders);


        ResponseEntity<KeyCloakUserDTO[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestHttpEntity,
                KeyCloakUserDTO[].class
        );
        KeyCloakUserDTO[] users = response.getBody();
        if(users!=null && users.length > 0){
            return users[0];
        }

        throw new Exception("User not found with username "+username);
    }

    public void assignRoleToUser(String userId, String clientId, List<KeyCloakRole> roles, String token) throws Exception {
        String url=KEYCLOAK_BASE_URL+"/admin/realms/master/users/"+userId+"/role-mappings/clients/"+clientId;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<List<KeyCloakRole>> requestHttpEntity = new HttpEntity<>(roles,httpHeaders);

        try{
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestHttpEntity,
                    String.class
            );

        }catch (Exception e){
            throw new Exception("Failed to assign new role"+e.getMessage());
        }
    }

    public KeyCloakUserDTO fetchUserProfileByJwt(String token) throws Exception {
        String url = KEYCLOAK_BASE_URL+"/realms/master/protocol/openid-connect/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try{
            ResponseEntity<KeyCloakUserDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    KeyCloakUserDTO.class
            );
            return response.getBody();
        }catch (Exception e){
            throw new Exception("Failed to get user info" + e.getMessage());
        }
    }



}
