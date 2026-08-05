package com.example.spring_data_jpa.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.MethodArgumentNotValidException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UserDTOTest {

    private final Validator validator;

    public UserDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testUserRequestValidation() {
        UserRequest user = new UserRequest();
        user.setUsername(" ");
        user.setEmail("invalid-email");
        user.setPassword("short");
        user.setFirstname(null);
        user.setLastname(null);

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(user);
        assertThat(violations.size()).isEqualTo(5);
    }
}
