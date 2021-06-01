package com.recipe.services;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.recipe.configurations.JwtTokenUtil;
import com.recipe.configurations.JwtUserDetails;
import com.recipe.db.LoginRepository;
import com.recipe.exceptions.FlowException;
import com.recipe.openapi.ErrorTypeEnum;
import com.recipe.openapi.LoginRequest;
import com.recipe.openapi.LoginResponse;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public LoginResponse authenticate(@Valid LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new FlowException(
                "Bad credentials.",
                ErrorTypeEnum.INTERNAL,
                HttpStatus.BAD_REQUEST);
        }

        var userDetails = (JwtUserDetails) jwtUserDetailsService.loadUserByUsername(loginRequest.getUsername());

        return new LoginResponse().accessToken(jwtTokenUtil.generateToken(userDetails));
    }
}
