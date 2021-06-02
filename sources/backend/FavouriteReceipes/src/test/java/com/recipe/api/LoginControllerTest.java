package com.recipe.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.recipe.services.LoginService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link LoginController}.
 */
@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private LoginService loginService;

    @Test
    void login() {
        // arrange
        var request = TestsControllerUtils.createValidLoginRequest();
        var response = TestsControllerUtils.createValidLoginResponse();
        when(loginService.authenticate(request)).thenReturn(response);

        // act
        var responseEntity = loginController.login(request);

        // assert
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
