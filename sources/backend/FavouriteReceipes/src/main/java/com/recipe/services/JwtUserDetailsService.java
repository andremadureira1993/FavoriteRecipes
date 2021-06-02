package com.recipe.services;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.recipe.configurations.JwtUserDetails;
import com.recipe.db.LoginRepository;
import com.recipe.exceptions.FlowException;
import com.recipe.openapi.ErrorTypeEnum;

/**
 * Load username in database and matchs password
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private LoginRepository repository;

    @Autowired
    private StringEncryptor encryptor;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var login = repository.findByUsername(username);

        if (login == null) {
            throw new FlowException(
                "User not found: " + username,
                ErrorTypeEnum.INTERNAL,
                HttpStatus.NOT_FOUND);
        }

        return new JwtUserDetails(login, encryptor.decrypt(login.getPassword()));
    }
}
