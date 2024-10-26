package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.paulnikepro.hw3.dto.UserRegistrationDto;
import org.paulnikepro.hw3.dto.UserResponseDto;
import org.paulnikepro.hw3.entity.User;
import org.paulnikepro.hw3.repository.UserRepository;
import org.paulnikepro.hw3.service.UserServiceImpl;
import org.paulnikepro.hw3.validation.UserValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        UserValidator userValidator = new UserValidator();
        userService = new UserServiceImpl(userRepository, userValidator);
    }

    @Test
    void testRegisterUser() {
        UserRegistrationDto registrationDto = new UserRegistrationDto("test@example.com", "+1234567890", "pass123", "pass123");
        User user = new User(null, "test@example.com", "+1234567890", "pass123");

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDto userResponse = userService.registerUser(registrationDto);

        assertEquals("test@example.com", userResponse.email());
    }
}
