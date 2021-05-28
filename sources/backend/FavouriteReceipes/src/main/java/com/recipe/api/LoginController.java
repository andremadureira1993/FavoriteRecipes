package com.recipe.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.recipe.openapi.LoginApi;

/**
 * A controller for login
 */
@Controller
public class LoginController implements LoginApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Override
    public ResponseEntity<Void> loginGet() {
        LOGGER.info("User login");

        return null;
    }
}
