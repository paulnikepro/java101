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
import org.paulnikepro.hw3.exception.UserServiceException;
import org.paulnikepro.hw3.repository.UserRepository;
import org.paulnikepro.hw3.service.UserServiceImpl;
import org.paulnikepro.hw3.validation.UserValidator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserValidator userValidator;

    @BeforeEach
    void setUp() {
        userValidator = new UserValidator();
        userService = new UserServiceImpl(userRepository, userValidator);
    }

    @Test
    void testRegisterUserSuccess() {
        UserRegistrationDto registrationDto = new UserRegistrationDto("test@example.com", "+1234567890", "pass123", "pass123");
        User user = new User(null, "test@example.com", "+1234567890", "pass123");

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDto userResponse = userService.registerUser(registrationDto);

        assertEquals("test@example.com", userResponse.email());
        assertEquals("+1234567890", userResponse.phoneNumber());
    }

    @Test
    void testRegisterUserInvalidEmail() {
        UserRegistrationDto registrationDto = new UserRegistrationDto("invalid-email", "+1234567890", "pass123", "pass123");

        UserServiceException exception = assertThrows(UserServiceException.class, () -> {
            userService.registerUser(registrationDto);
        });

        assertEquals("INVALID_EMAIL", exception.getCode());
    }

    @Test
    void testRegisterUserInvalidPhoneNumber() {
        UserRegistrationDto registrationDto = new UserRegistrationDto("test@example.com", "wrong-number", "pass123", "pass123");

        UserServiceException exception = assertThrows(UserServiceException.class, () -> {
            userService.registerUser(registrationDto);
        });

        assertEquals("INVALID_PHONE_NUMBER", exception.getCode());
    }

    @Test
    void testRegisterUserMismatchedPasswords() {
        UserRegistrationDto registrationDto = new UserRegistrationDto("test@example.com", "+1234567890", "pass123", "diffPass");

        UserServiceException exception = assertThrows(UserServiceException.class, () -> {
            userService.registerUser(registrationDto);
        });

        assertEquals("PASSWORD_MISMATCH", exception.getCode());
    }

    @Test
    void testGetUserByIdUserExists() {
        User user = new User(1L, "test@example.com", "+1234567890", "pass123");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDto userResponse = userService.getUserById(1L);

        assertEquals(1L, userResponse.id());
        assertEquals("test@example.com", userResponse.email());
        assertEquals("+1234567890", userResponse.phoneNumber());
    }

    @Test
    void testGetUserByIdUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserServiceException exception = assertThrows(UserServiceException.class, () -> {
            userService.getUserById(1L);
        });

        assertEquals("USER_NOT_FOUND", exception.getCode());
    }

    @Test
    void testRegisterUserOptionalPhoneNumber() {
        UserRegistrationDto registrationDto = new UserRegistrationDto("test@example.com", null, "pass123", "pass123");
        User user = new User(null, "test@example.com", null, "pass123");

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDto userResponse = userService.registerUser(registrationDto);

        assertEquals("test@example.com", userResponse.email());
        assertNull(userResponse.phoneNumber());
    }
}
