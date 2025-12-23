package com.yoho.service;

import com.yoho.payload.response.AuthResponse;
import com.yoho.payload.response.dto.SignupDTO;

public interface AuthService {

    AuthResponse login(String username, String password) throws Exception;
    AuthResponse signup(SignupDTO req) throws Exception;
    AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception;
}
