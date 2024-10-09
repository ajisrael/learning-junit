package com.example.service;

import com.example.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        assertEquals(firstName, user.getFirstName(), "User's first name is incorrect");
        assertEquals(lastName, user.getLastName(), "User's last name is incorrect");
        assertEquals(email, user.getEmail(), "User's email is incorrect");
    }
}
