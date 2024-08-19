package com.aarash.demo.uitil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordUtil {
    private final PasswordEncoder passwordEncoder;

    public String getHashedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matchPasswords(String password, String hashedPassword){
        return passwordEncoder.matches(password, hashedPassword);
    }
}
