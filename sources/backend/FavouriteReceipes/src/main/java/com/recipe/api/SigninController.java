package com.recipe.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.recipe.openapi.SigninApi;


/**
 * A controller for login
 */
@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class SigninController implements SigninApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(SigninController.class);

    @Override
    public ResponseEntity<Void> signinGet() {
        return ResponseEntity.ok(null);
    }
}
