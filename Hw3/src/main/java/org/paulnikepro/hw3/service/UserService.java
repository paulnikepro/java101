package org.paulnikepro.hw3.service;

import org.paulnikepro.hw3.dto.UserRegistrationDto;
import org.paulnikepro.hw3.dto.UserResponseDto;

public interface UserService {
    UserResponseDto registerUser(UserRegistrationDto userRegistrationDto);

    UserResponseDto getUserById(Long userId);
}
