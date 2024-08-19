package com.aarash.demo.dto;

public class UserPasswordDto {
    private String password;
    private String newPassword;

    public UserPasswordDto() {
    }

    public UserPasswordDto(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
