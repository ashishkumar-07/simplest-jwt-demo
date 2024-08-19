package com.aarash.demo.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;


public class CustomJwtAuthenticationConverter extends JwtAuthenticationConverter {
    public CustomJwtAuthenticationConverter(String authorityPrefix, String authorityClaimName) {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter =
                new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix(authorityPrefix);
        grantedAuthoritiesConverter.setAuthoritiesClaimName(authorityClaimName);
        this.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    }

}
