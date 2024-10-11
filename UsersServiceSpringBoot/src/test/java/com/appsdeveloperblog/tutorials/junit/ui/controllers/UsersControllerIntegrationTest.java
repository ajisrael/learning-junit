package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersControllerIntegrationTest {

    @Test
    @DisplayName("User can be created")
    void testCreateUser_whenValidDetailsProvided_returnsUserDetails() {
        // Arrange
//        String createUserJson = "{\n" +
//                "   \"firstName\":\"Sergey\",\n" +
//                "   \"lastName\":\"Kargopolov\",\n" +
//                "   \"email\":\"test@test.com\",\n" +
//                "   \"password\":\"12345678\",\n" +
//                "   \"repeatPassword\":\"12345678\",\n" +
//                "}";

        // Act

        // Assert

    }
}
