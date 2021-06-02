package com.recipe.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.recipe.api.TestsControllerUtils;
import com.recipe.db.Login;
import com.recipe.db.LoginRepository;
import com.recipe.exceptions.FlowException;
import com.recipe.services.SignupService;
import com.recipe.util.Utils;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

/**
 * Integration tests for {@link SignupService}.
 */
@ExtendWith(MockitoExtension.class)
public class SignupServiceTest {
    @InjectMocks
    private SignupService signupService;

    @Mock
    private LoginRepository loginRepository;

    @Mock
    private Utils utils;

    @Test
    void addInvalidUser() {
        // arrange
        var request = TestsServicesUtils.createInvalidSignUpRequest();

        // act and assert
        assertThatThrownBy(
            () -> signupService.addUser(request))
            .isInstanceOf(FlowException.class);
    }

    @Test
    void addValidUser() {
        // arrange
        var request = TestsServicesUtils.createValidSignUpRequest("andre", "andre");

        // act and assert
        Assertions.assertDoesNotThrow(() -> signupService.addUser(request));
    }

    @Test
    void addDuplicatedUser() {
        // arrange
        var request = TestsServicesUtils.createValidSignUpRequest("andre", "andre");
        var loginFounded = TestsServicesUtils.createValidLogin();
        when(loginRepository.findByUsername("andre")).thenReturn(loginFounded);

        // act and assert
        assertThatThrownBy(
            () -> signupService.addUser(request))
            .isInstanceOf(FlowException.class);
    }
}
