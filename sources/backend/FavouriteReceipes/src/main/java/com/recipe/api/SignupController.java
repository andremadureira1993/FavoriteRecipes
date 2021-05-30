package com.recipe.api;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.recipe.services.SignupService;
import com.recipeData.openapi.SignUpRequest;
import com.recipeData.openapi.SignupApi;

/**
 * A controller for login
 */
@Controller
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
