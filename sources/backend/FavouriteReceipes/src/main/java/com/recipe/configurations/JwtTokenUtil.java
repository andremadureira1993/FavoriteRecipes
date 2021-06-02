package com.recipe.configurations;

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

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
}
