package com.aarash.demo.dto;

import com.aarash.demo.model.UserRole;
import lombok.Data;

@Data
public class UserRegisterDto {
    private String username;
    private String password;
    private String email;
    private UserRole role;
    private String firstName;
    private String lastName;
}
