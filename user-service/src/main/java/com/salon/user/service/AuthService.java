package com.salon.user.service;

import com.salon.user.payload.response.AuthResponse;
import com.salon.user.payload.response.dto.SignupDTO;

public interface AuthService {

    AuthResponse login(String username, String password) throws Exception;
    AuthResponse signup(SignupDTO req) throws Exception;
    AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception;
}
