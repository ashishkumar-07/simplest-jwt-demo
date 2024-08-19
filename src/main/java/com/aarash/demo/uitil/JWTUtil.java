package com.aarash.demo.uitil;

import com.aarash.demo.model.Users;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class JWTUtil {

    @Value("${jwt.expiration.hours}")
    private int expirationHours;

    private final JwtEncoder encoder;

    @SneakyThrows
    public String generateToken(Users user) {
        Instant now = Instant.now();
        String role = user.getRole().name();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("Aarash")
                .issuedAt(now)
                .expiresAt(now.plus(expirationHours, ChronoUnit.HOURS))
                .subject(user.getUsername())
                .claim("roles", role)
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .build();
        JwtEncoderParameters encoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS512).build(), claims);
        return this.encoder.encode(encoderParameters).getTokenValue();
    }
}
