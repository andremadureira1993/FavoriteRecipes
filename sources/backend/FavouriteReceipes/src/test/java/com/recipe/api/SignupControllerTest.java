package com.recipe.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.recipe.services.SignupService;

import static org.mockito.Mockito.doNothing;

/**
 * Unit tests for {@link SignupController}.
 */
@ExtendWith(MockitoExtension.class)
public class SignupControllerTest {

    @InjectMocks
    private SignupController signupController;

    @Mock
    private SignupService signupService;

    @Test
    void signupPost() {
        // arrange
        var request = TestsControllerUtils.createValidSignUpRequest();
        doNothing().when(signupService).addUser(request);

        // act
        var responseEntity = signupController.signupPost(request);

        // assert
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
