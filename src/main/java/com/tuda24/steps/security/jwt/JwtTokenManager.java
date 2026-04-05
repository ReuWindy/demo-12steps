package com.tuda24.steps.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tuda24.steps.entity.Role;
import com.tuda24.steps.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtTokenManager {

    private final JwtProperties jwtProperties;

    public JwtTokenManager(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateAccessToken(User user) {

        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .map(roleName -> roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName)
                .toList();

        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuer(jwtProperties.getIssuer())
                .withClaim("roles", roles)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration()))
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey().getBytes()));
    }

    public String getUsernameFromToken(String token) {
        return getDecodedJWT(token).getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        return getDecodedJWT(token).getClaim("roles").asList(String.class);
    }

    public String resolveTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            getDecodedJWT(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    private DecodedJWT getDecodedJWT(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey().getBytes()))
                .withIssuer(jwtProperties.getIssuer())
                .build();

        return verifier.verify(token);
    }
}