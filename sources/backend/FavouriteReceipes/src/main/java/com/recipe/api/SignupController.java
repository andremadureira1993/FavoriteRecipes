package com.recipe.api;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.openapi.SignUpRequest;
import com.recipe.openapi.SignupApi;
import com.recipe.services.SignupService;

/**
 * A controller for login
 */
@Controller
public class SignupController implements SignupApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignupController.class);

    @Autowired
    private SignupService signupService;

    @Override
    public ResponseEntity<String> signupPost(@Valid SignUpRequest signUpRequest) {
        ObjectMapper objectMapper = new ObjectMapper();

        signupService.addUser(signUpRequest);
        return null;
    }
}
