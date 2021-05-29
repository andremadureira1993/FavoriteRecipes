package com.recipe.configurations;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * Sets the authentication manager
 * The implementation was based in this link: https://grobmeier.solutions/spring-security-5-jwt-basic-auth.html
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private String jwtAudience;
    private String jwtIssuer;
    private String jwtSecret;
    private String jwtType;
    private JdbcTemplate jdbcTemplate;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JdbcTemplate jdbcTemplate,
                                  String jwtAudience, String jwtIssuer, String jwtSecret, String jwtType) {
        super(authenticationManager);

        this.jdbcTemplate = jdbcTemplate;
        this.jwtAudience = jwtAudience;
        this.jwtIssuer = jwtIssuer;
        this.jwtSecret = jwtSecret;
        this.jwtType = jwtType;
    }


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JdbcTemplate jdbcTemplate,
                                   String jwtAudience, String jwtIssuer, String jwtSecret, String jwtType) {
        this.jwtAudience = jwtAudience;
        this.jwtIssuer = jwtIssuer;
        this.jwtSecret = jwtSecret;
        this.jwtType = jwtType;
        this.jdbcTemplate = jdbcTemplate;
        this.setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl("/login");
    }

    @Override
    protected void successfulAuthentication(
        HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain, Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        String token = Jwts.builder()
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .setHeaderParam("typ", jwtType)
            .setIssuer(jwtIssuer)
            .setAudience(jwtAudience)
            .setSubject(user.getUsername())
            .setExpiration(new Date(System.currentTimeMillis() + 864000000))
            .compact();

        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    private UsernamePasswordAuthenticationToken parseToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith("Bearer ")) {
            String claims = token.replace("Bearer ", "");
            try {
                Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes())
                    .parseClaimsJws(claims);

                String username = claimsJws.getBody().getSubject();

                if ("".equals(username) || username == null) {
                    return null;
                }

                List<SimpleGrantedAuthority> authorities = jdbcTemplate.queryForList(
                    "SELECT a.authority " +
                        "FROM user_authorities a, users u " +
                        "WHERE u.username = ? " +
                        "AND u.id = a.user_id", String.class, username)
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

                return new UsernamePasswordAuthenticationToken(username, null, null);
            } catch (JwtException exception) {
//                log.warn("Some exception : {} failed : {}", token, exception.getMessage());
            }
        }


        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = parseToken(request);

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}