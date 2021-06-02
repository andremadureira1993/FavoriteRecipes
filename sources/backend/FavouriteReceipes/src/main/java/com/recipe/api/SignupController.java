package com.recipe.api;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.recipe.services.SignupService;
import com.recipe.openapi.SignUpRequest;
import com.recipe.openapi.SignupApi;

/**
 * A controller for login
 */
@Controller
@CrossOrigin
public class SignupController implements SignupApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignupController.class);

    @Autowired
    private SignupService signupService;

    @Override
    public ResponseEntity<Void> signupPost(@Valid SignUpRequest signUpRequest) {
        signupService.addUser(signUpRequest);
        return ResponseEntity.ok(null);
    }
}
