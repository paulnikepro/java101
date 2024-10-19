package org.paulnikepro.hw3.validation;

import org.paulnikepro.hw3.dto.UserRegistrationDto;

import java.util.regex.Pattern;

public class UserValidator {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PHONE_REGEX = "^\\+?[0-9]{10,15}$"; // Example regex for phone numbers

    public void validate(UserRegistrationDto dto) {
        if (!Pattern.matches(EMAIL_REGEX, dto.getEmail())) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        if (dto.getPhoneNumber() != null && !Pattern.matches(PHONE_REGEX, dto.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid phone number.");
        }

        if (!dto.getPassword().equals(dto.getRepeatPassword())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }
    }
}
