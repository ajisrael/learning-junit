package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import com.appsdeveloperblog.tutorials.junit.ui.response.UserRest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("User can be created")
    void testCreateUser_whenValidDetailsProvided_returnsUserDetails() throws JSONException {
        // Arrange
        JSONObject userDetailsRequestJson = new JSONObject();
        userDetailsRequestJson.put("firstName", "Sergey");
        userDetailsRequestJson.put("lastName", "Kargopolov");
        userDetailsRequestJson.put("email", "test@test.com");
        userDetailsRequestJson.put("password", "12345678");
        userDetailsRequestJson.put("repeatPassword", "12345678");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(userDetailsRequestJson.toString(), headers);

        // Act
        ResponseEntity<UserRest> createdUserResponseEntity =
                testRestTemplate.postForEntity("/users", request, UserRest.class);
        UserRest createdUserDetails = createdUserResponseEntity.getBody();

        // Assert
        assertEquals(HttpStatus.OK, createdUserResponseEntity.getStatusCode(),
                "Status code should be 200 OK");
        assertEquals(userDetailsRequestJson.get("firstName"), createdUserDetails.getFirstName(),
                "Returned user's first name is incorrect");
        assertEquals(userDetailsRequestJson.get("lastName"), createdUserDetails.getLastName(),
                "Returned user's last name is incorrect");
        assertEquals(userDetailsRequestJson.get("email"), createdUserDetails.getEmail(),
                "Returned user's email is incorrect");
        assertFalse(createdUserDetails.getUserId().isEmpty(),
                "User id should not be empty");
    }
}
