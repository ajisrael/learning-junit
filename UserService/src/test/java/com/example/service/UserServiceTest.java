package com.example.service;

import com.example.data.UsersRepository;
import com.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.constants.ExceptionMessages.CANNOT_CREATE_USER;
import static com.example.constants.ExceptionMessages.FIRST_NAME_IS_EMPTY;
import static com.example.constants.ExceptionMessages.LAST_NAME_IS_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UsersRepository usersRepository;
    @Mock
    EmailVerificationServiceImpl emailVerificationService;

    @InjectMocks
    UserServiceImpl userService;
    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;

    @BeforeEach
    void init() {
        firstName = "Sergey";
        lastName = "Kargopolov";
        email = "test@test.com";
        password = "12345678";
        repeatPassword = "12345678";
    }

    @Test
    @DisplayName("User Object Created")
    void testCreateUser_whenUserDetailsProvided_returnsUserObject() {
        // Arrange
        when(usersRepository.save(any(User.class))).thenReturn(true);
        doNothing().when(emailVerificationService).scheduleEmailConfirmation(any(User.class));

        // Act
        User user = userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
        assertNotNull(user, "the createUser() should not have returned null");
        assertNotNull(user.getId(), "User should not have a null id");
        assertEquals(firstName, user.getFirstName(), "User's first name is incorrect");
        assertEquals(lastName, user.getLastName(), "User's last name is incorrect");
        assertEquals(email, user.getEmail(), "User's email is incorrect");
        verify(usersRepository, times(1)).save(any(User.class));
        verify(emailVerificationService, times(1)).scheduleEmailConfirmation(any(User.class));
    }

    @Test
    @DisplayName("User Object Creation Failed")
    void testCreateUser_whenSavingUserToRepositoryFails_thenThrowsUserServiceException() {
        // Arrange
        when(usersRepository.save(any(User.class))).thenReturn(false);

        // Act & Assert
        UserServiceException exception = assertThrows(UserServiceException.class, () -> {
            User user = userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "User service should throw UserServiceException when repository throws exception");

        // Assert
        assertEquals(CANNOT_CREATE_USER, exception.getLocalizedMessage(),
                "Message from User Service Exception is incorrect");
        verify(usersRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Empty first name causes correct exception")
    void testCreateUser_whenFirstNameIsEmpty_throwsIllegalArgumentException() {
        // Arrange
        String firstName = "";

        // Act && Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            User user = userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Empty first name should have caused an Illegal Argument Exception");

        // Assert
        assertEquals(FIRST_NAME_IS_EMPTY, exception.getLocalizedMessage(),
                "Message from Illegal Argument Exception is incorrect");
    }

    @Test
    @DisplayName("Empty last name causes correct exception")
    void testCreateUser_whenLastNameIsEmpty_throwsIllegalArgumentException() {
        // Arrange
        String lastName = "";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            User user = userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Empty last name should have caused an Illegal Argument Exception");

        // Assert
        assertEquals(LAST_NAME_IS_EMPTY, exception.getLocalizedMessage(),
                "Message from Illegal Argument Exception is incorrect");
    }

    @Test
    @DisplayName("Throw UserServiceException when save() throws RuntimeException")
    void testCreateUser_whenSaveMethodThrowsException_thenThrowsUserServiceException() {
        // Arrange
        when(usersRepository.save(any(User.class))).thenThrow(RuntimeException.class);

        // Act & Assert
        UserServiceException exception = assertThrows(UserServiceException.class, () -> {
            User user = userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "User service should throw UserServiceException when repository throws exception");

        //Assert
        verify(usersRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Throw UserServiceException when scheduleEmailConfirmation() throws RuntimeException")
    void testCreateUser_whenEmailNotificationExceptionThrown_throwsUserServiceException() {
        // Arrange
        when(usersRepository.save(any(User.class))).thenReturn(true);

        doThrow(EmailNotificationServiceException.class)
                .when(emailVerificationService)
                .scheduleEmailConfirmation(any(User.class));

        // Act & Assert
        UserServiceException exception = assertThrows(UserServiceException.class, () -> {
            User user = userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "User service should throw UserServiceException when emailVerificationService throws exception");

        // Assert
        verify(emailVerificationService, times(1)).scheduleEmailConfirmation(any(User.class));
    }

    @Test
    @DisplayName("Schedule Email Confirmation is executed")
    void testCreateUser_whenUserCreated_schedulesEmailConfirmation() {
        // Arrange
        when(usersRepository.save(any(User.class))).thenReturn(true);

        doCallRealMethod().when(emailVerificationService)
                .scheduleEmailConfirmation(any(User.class));

        // Act
        userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
        verify(emailVerificationService, times(1))
                .scheduleEmailConfirmation(any(User.class));
    }
}
