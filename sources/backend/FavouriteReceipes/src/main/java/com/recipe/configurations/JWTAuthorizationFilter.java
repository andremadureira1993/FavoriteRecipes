package com.recipe.configurations;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import static com.recipe.configurations.SecurityConstants.HEADER;
import static com.recipe.configurations.SecurityConstants.SECRET;
import static com.recipe.configurations.SecurityConstants.TOKEN_PREFIX;


import com.recipe.services.CustomUserDetailService;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private final CustomUserDetailService customUserDetailService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, CustomUserDetailService customUserDetailService) {
        super(authenticationManager);
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String headerAuthorization = request.getHeader(HEADER);

        if (StringUtils.isBlank(headerAuthorization) || !headerAuthorization.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return ;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(headerAuthorization);
        updateChainFilter(request, response, chain, authenticationToken);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String headerAuthorization) {
        headerAuthorization = headerAuthorization.replace(TOKEN_PREFIX, "");

        String username = Jwts.
            parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(headerAuthorization)
            .getBody()
            .getSubject();

        return getAuthenticationTokenByUsername(username);

    }

    private UsernamePasswordAuthenticationToken getAuthenticationTokenByUsername(String username) {
        if (StringUtils.isBlank(username)) return null;

        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());

    }

    private void updateChainFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain, UsernamePasswordAuthenticationToken authenticationToken) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }


}
