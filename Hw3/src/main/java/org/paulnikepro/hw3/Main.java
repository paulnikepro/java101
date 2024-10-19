package org.paulnikepro.hw3;

import org.paulnikepro.hw3.dto.UserRegistrationDto;
import org.paulnikepro.hw3.dto.UserResponseDto;
import org.paulnikepro.hw3.repository.UserRepositoryImpl;
import org.paulnikepro.hw3.service.UserService;
import org.paulnikepro.hw3.service.UserServiceImpl;
import org.paulnikepro.hw3.validation.UserValidator;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl(new UserRepositoryImpl(), new UserValidator());

        // Registration Flow
        UserRegistrationDto registrationDto = new UserRegistrationDto("john.doe@example.com", "+1234567890", "password123", "password123");
        UserResponseDto registeredUser = userService.registerUser(registrationDto);
        System.out.println("Registered User: " + registeredUser);

        // Get user by ID
        UserResponseDto userById = userService.getUserById(registeredUser.id());
        System.out.println("Fetched User: " + userById);
    }
}

