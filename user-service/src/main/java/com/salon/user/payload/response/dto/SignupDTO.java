package com.salon.user.payload.response.dto;

import com.salon.user.domain.UserRole;
import lombok.Data;

@Data
public class SignupDTO {

    private String fullName;
    private String email;
    private String password;
    private String username;
    private UserRole role;
}
