package com.salon.user.payload.response;

import com.salon.user.domain.UserRole;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String refresh_token;
    private String message;
    private String title;
    private UserRole role;
}
