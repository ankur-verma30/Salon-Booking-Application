package com.salon.user.service.impl;

import com.salon.user.entity.User;
import com.salon.user.payload.response.AuthResponse;
import com.salon.user.payload.response.dto.SignupDTO;
import com.salon.user.payload.response.dto.TokenResponse;
import com.salon.user.repository.UserRepository;
import com.salon.user.service.AuthService;
import com.salon.user.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;

    @Override
    public AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception {
        TokenResponse tokenResponse = keycloakService.getAdminAccessToken(null,
                null,
                "refresh_token", refreshToken);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setRefresh_token(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());
        authResponse.setMessage("Login success");
        return authResponse;
    }

    @Override
    public AuthResponse login(String username, String password) throws Exception {
        TokenResponse tokenResponse = keycloakService.getAdminAccessToken(username,
                password,
                "password",
                null);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setRefresh_token(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());
        authResponse.setMessage("Login success");
        return authResponse;
    }

    @Override
    public AuthResponse signup(SignupDTO req) throws Exception {
        keycloakService.createUser(req);

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());
        user.setEmail(req.getEmail());
        user.setRole(req.getRole());
        user.setFullName(req.getFullName());
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        TokenResponse tokenResponse = keycloakService.getAdminAccessToken(req.getUsername(),
                req.getPassword(),
                "password",
                null);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setRefresh_token(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());
        authResponse.setRole(user.getRole());
        authResponse.setMessage("Register success");

        return authResponse;
    }
}
