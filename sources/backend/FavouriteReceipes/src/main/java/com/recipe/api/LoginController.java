package com.recipe.api;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.recipe.openapi.LoginApi;
import com.recipe.openapi.LoginRequest;
import com.recipe.openapi.LoginResponse;
import com.recipe.services.LoginService;

@Controller
public class LoginController implements LoginApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @Override
    public ResponseEntity<LoginResponse> login(@Valid LoginRequest loginRequest) {
        LOGGER.info("Processing login for user: " + loginRequest.getUsername());
        return ResponseEntity.ok(loginService.authenticate(loginRequest));
    }
}
