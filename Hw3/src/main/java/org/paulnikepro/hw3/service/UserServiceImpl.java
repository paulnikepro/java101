package org.paulnikepro.hw3.service;

import org.paulnikepro.hw3.dto.UserRegistrationDto;
import org.paulnikepro.hw3.dto.UserResponseDto;
import org.paulnikepro.hw3.entity.User;
import org.paulnikepro.hw3.repository.UserRepository;
import org.paulnikepro.hw3.validation.UserValidator;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public UserServiceImpl(UserRepository userRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    @Override
    public UserResponseDto registerUser(UserRegistrationDto userRegistrationDto) {
        userValidator.validate(userRegistrationDto);

        User user = new User(null, userRegistrationDto.getEmail(), userRegistrationDto.getPhoneNumber(), userRegistrationDto.getPassword());
        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser.getId(), savedUser.getEmail(), savedUser.getPhoneNumber());
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        return new UserResponseDto(user.getId(), user.getEmail(), user.getPhoneNumber());
    }
}
