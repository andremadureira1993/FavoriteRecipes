package com.recipe.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Integration tests for {@link LoginRepository}.
 */
@ExtendWith({SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginRepositoryIT {
    @Autowired
    private LoginRepository loginRepository;

    @BeforeEach
    void setup() {
        loginRepository.deleteAll();
    }

    @Test
    void canSave() {
        // arrange
        var login = TestsRepositoryUtils.createValidLogin();

        // act and assert
        Assertions.assertDoesNotThrow(() -> loginRepository.save(login));
    }

}
