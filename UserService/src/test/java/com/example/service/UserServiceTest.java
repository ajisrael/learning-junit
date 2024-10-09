package com.example.service;

import com.example.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.constants.ExceptionMessages.FIRST_NAME_IS_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {

    @Test
    @DisplayName("User Object Created")
    void testCreateUser_whenUserDetailsProvided_returnsUserObject() {
        // Arrange
        UserService userService = new UserServiceImpl();
        String firstName = "Sergey";
        String lastName = "Kargopolov";
        String email = "test@test.com";
        String password = "12345678";
        String repeatPassword = "12345678";

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
        UserService userService = new UserServiceImpl();
        String firstName = "";
        String lastName = "Kargopolov";
        String email = "test@test.com";
        String password = "12345678";
        String repeatPassword = "12345678";

        String expectedExceptionMessage = FIRST_NAME_IS_EMPTY;

        // Act && Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            User user = userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Empty first name should have caused an Illegal Argument Exception");

        // Assert
        assertEquals(expectedExceptionMessage, exception.getLocalizedMessage(),
                "Message from Illegal Argument Exception is incorrect");
    }
}
