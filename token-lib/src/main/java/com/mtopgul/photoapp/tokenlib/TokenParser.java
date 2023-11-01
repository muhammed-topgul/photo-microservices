package com.mtopgul.photoapp.tokenlib;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author muhammed-topgul
 * @since 01/11/2023 15:18
 */
public class TokenParser {
    private final Jwt<?, ?> jwtObject;

    private TokenParser(String jwt, String secretToken) {
        this.jwtObject = toJwt(jwt, secretToken);
    }

    public static TokenParser build(String jwt, String secretToken) {
        return new TokenParser(jwt, secretToken);
    }

    public Jwt<?, ?> toJwt(String jwt, String secretToken) {
        byte[] secretKeyBytes = Base64.getEncoder().encode(secretToken.getBytes());
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parse(jwt);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<Map<String, String>> scopes = ((Claims) jwtObject.getBody()).get("scope", List.class);
        return scopes.stream()
                .map(s -> new SimpleGrantedAuthority(s.get("authority")))
                .toList();
    }

    public String getSubject() {
        return ((Claims) jwtObject.getBody()).getSubject();
    }
}
