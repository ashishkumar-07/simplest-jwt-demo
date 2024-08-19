package com.aarash.demo.controller;

import com.aarash.demo.controller.service.UserService;
import com.aarash.demo.dto.LoginResponse;
import com.aarash.demo.dto.UserLoginDto;
import com.aarash.demo.dto.UserPasswordDto;
import com.aarash.demo.dto.UserRegisterDto;
import com.aarash.demo.model.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public Users registerUser(@RequestBody UserRegisterDto registerDto) {
        return userService.registerUser(registerDto);
    }


    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody UserLoginDto loginDto) {
        return userService.loginUser(loginDto);
    }

    @GetMapping("/me")
    public Users me() {
        return userService.me();
    }

    @GetMapping("/profile/{username}")
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.name")
    public Users userProfile(@PathVariable String username) {
        return userService.me();
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') ")
    public void deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
    }

    @PatchMapping("/{username}")
    @PreAuthorize("hasRole('USER') and #username == authentication.name")
    public void updateUserPassword(@PathVariable String username, @RequestBody UserPasswordDto passwordDto) {
        userService.updateUserPassword(username, passwordDto);
    }
}
