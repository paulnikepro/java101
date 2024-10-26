package test;

import org.paulnikepro.hw3.dto.UserRegistrationDto;
import org.junit.jupiter.api.Test;
import org.paulnikepro.hw3.validation.UserValidator;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    private final UserValidator userValidator = new UserValidator();

    @Test
    void testValidUser() {
        UserRegistrationDto dto = new UserRegistrationDto("test@example.com", "+1234567890", "password", "password");
        assertDoesNotThrow(() -> userValidator.validate(dto));
    }

    @Test
    void testInvalidEmail() {
        UserRegistrationDto dto = new UserRegistrationDto("invalid-email", "+1234567890", "password", "password");
        assertThrows(IllegalArgumentException.class, () -> userValidator.validate(dto));
    }

    @Test
    void testPasswordsMismatch() {
        UserRegistrationDto dto = new UserRegistrationDto("test@example.com", "+1234567890", "password", "wrongpassword");
        assertThrows(IllegalArgumentException.class, () -> userValidator.validate(dto));
    }
}

