package com.recipe.services;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.recipe.db.Login;
import com.recipe.db.LoginRepository;
import com.recipe.exceptions.FlowException;
import com.recipe.openapi.ErrorTypeEnum;
import com.recipe.util.Utils;
import com.recipe.openapi.SignUpRequest;

/**
 * Process signup requests
 */
@Service
public class SignupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignupService.class);

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private Utils utils;

    /**
     * Add user into a database
     * @param signUpRequest
     */
    public void addUser(SignUpRequest signUpRequest) {
        if (StringUtils.isBlank(signUpRequest.getUsername()) || StringUtils.isBlank(signUpRequest.getPassword())) {
            throw new FlowException("Invalid credentials to signup",
                ErrorTypeEnum.INVALID_REQUEST,
                HttpStatus.BAD_REQUEST);
        };

        if (isUsernameAlreadyInUse(signUpRequest.getUsername())) {
            throw new FlowException("Could not create the username it's already registered",
                ErrorTypeEnum.INVALID_REQUEST,
                HttpStatus.BAD_REQUEST);
        } else {
            createUserInDatabase(signUpRequest);
        }
    }

    /**
     * Check if the username its already in database
     * @param username
     * @return
     */
    public boolean isUsernameAlreadyInUse(String username) {
        Login login = loginRepository.findByUsername(username);

        if (login != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Create a user new user in database
     * @param signUpRequest
     */
    public void createUserInDatabase(SignUpRequest signUpRequest) {
        Login login = new Login();

        login.setUsername(signUpRequest.getUsername());
        String encodedPassword = utils.encodePassword(signUpRequest.getPassword());
//        String encodedPassword = signUpRequest.getPassword();
        login.setPassword(encodedPassword);
        loginRepository.save(login);
        LOGGER.info("Successfully created user " + signUpRequest.getUsername() + " in database");
    }
}
