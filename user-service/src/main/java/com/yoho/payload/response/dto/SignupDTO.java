package com.yoho.payload.response.dto;

import com.yoho.domain.UserRole;
import lombok.Data;

@Data
public class SignupDTO {

    private String fullName;
    private String email;
    private String password;
    private String username;
    private UserRole role;
}
