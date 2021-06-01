package com.recipe.configurations;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

    public static final long JWT_ACCESS_TOKEN_VALIDITY = 7 * 24 * 60 * 60; // A week.

    public static final long JWT_REFRESH_TOKEN_VALIDITY = 30 * 24 * 60 * 60; // A month.

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Gets the JWT subject (in this case the user email).
     *
     * @param token
     * @return The JWT subject.
     */
    public String getSubjectFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Gets the JWT expiration date.
     *
     * @param token
     * @return The JWT expiration date.
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Gets all claims from a JWT.
     *
     * @param token
     * @return All claims.
     */
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Generates a JWT.
     *
     * @param userDetails
     * @return A JWT.
     */
    public String generateToken(JwtUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_VALIDITY * 1000))
            .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * Validates a JWT.
     *
     * @param token
     * @param userDetails
     * @return True, if valid. False, if invalid.
     */
    public Boolean validateToken(String token, JwtUserDetails userDetails) {
        final String username = getSubjectFromToken(token);
        return username.equals(userDetails.getUsername());
    }

    /**
     * Generates a JWT.
     *
     * @param subject
     * @return A JWT.
     */
    public String generateRefreshToken(String subject) {
        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY * 1000))
            .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
}
