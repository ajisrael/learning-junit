package com.example.service;

import com.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.constants.ExceptionMessages.FIRST_NAME_IS_EMPTY;
import static com.example.constants.ExceptionMessages.LAST_NAME_IS_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {

    UserService userService;
    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;

    @BeforeEach
    void init() {
        userService = new UserServiceImpl();
        firstName = "Sergey";
        lastName = "Kargopolov";
        email = "test@test.com";
        password = "12345678";
        repeatPassword = "12345678";
    }

    @Test
    @DisplayName("User Object Created")
    void testCreateUser_whenUserDetailsProvided_returnsUserObject() {
        // Act
        User user = userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
        assertNotNull(user, "the createUser() should not have returned null");
        assertNotNull(user.getId(), "User should not have a null id");
        assertEquals(firstName, user.getFirstName(), "User's first name is incorrect");
        assertEquals(lastName, user.getLastName(), "User's last name is incorrect");
        assertEquals(email, user.getEmail(), "User's email is incorrect");
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

        // Act && Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            User user = userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Empty last name should have caused an Illegal Argument Exception");

        // Assert
        assertEquals(LAST_NAME_IS_EMPTY, exception.getLocalizedMessage(),
                "Message from Illegal Argument Exception is incorrect");
    }
}
