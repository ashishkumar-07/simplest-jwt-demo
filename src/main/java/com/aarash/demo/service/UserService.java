package com.aarash.demo.service;

import com.aarash.demo.dto.LoginResponse;
import com.aarash.demo.dto.UserLoginDto;
import com.aarash.demo.dto.UserPasswordDto;
import com.aarash.demo.dto.UserRegisterDto;
import com.aarash.demo.exception.BadRequestException;
import com.aarash.demo.exception.UnAuthorizedException;
import com.aarash.demo.model.Users;
import com.aarash.demo.reporsitory.UserRepository;
import com.aarash.demo.uitil.JWTUtil;
import com.aarash.demo.uitil.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    private final JWTUtil jwtUtil;

    private final PasswordUtil passwordUtil;

    public Users registerUser(UserRegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists");
        }
        Users user = new Users();
        BeanUtils.copyProperties(registerDto, user);
        user.setPasswordHash(passwordUtil.getHashedPassword(registerDto.getPassword()));

        return userRepository.save(user);
    }

    public LoginResponse loginUser(UserLoginDto loginDto) {
        log.info("Login request for user {}", loginDto.username());
        Users user = userRepository.findByUsername(loginDto.username())
                .orElseThrow(() -> new UnAuthorizedException("Username does not exists"));

        if (!passwordUtil.matchPasswords(loginDto.password(), user.getPasswordHash())) {
            throw new UnAuthorizedException("Password does not match.");
        }
        String token = jwtUtil.generateToken(user);
        return LoginResponse.builder()
                .token(token)
                .build();
    }

    public Users me() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UnAuthorizedException("User not found"));
    }

    public Users userProfile(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UnAuthorizedException("User not found"));
    }

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    public void updateUserPassword(String username, UserPasswordDto passwordDto) {
        Users user = userRepository.findByUsername(username).orElseThrow(()->new UnAuthorizedException("User not found"));

        if (!passwordUtil.matchPasswords(passwordDto.getPassword(), user.getPasswordHash())) {
            throw new UnAuthorizedException("Current password does not match.");
        }
        String hashedPassword = passwordUtil.getHashedPassword(passwordDto.getPassword());
        if (passwordUtil.matchPasswords(user.getPasswordHash(), hashedPassword)) {
            throw new UnAuthorizedException("New password cannot be the same as the current password.");
        }
        user.setPasswordHash(hashedPassword);
        userRepository.save(user);
    }
}
