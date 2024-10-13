package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import com.appsdeveloperblog.tutorials.junit.security.SecurityConstants;
import com.appsdeveloperblog.tutorials.junit.ui.response.UserRest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsersControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String authorizationToken;

    @Test
    @Order(1)
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

    @Test
    @Order(2)
    @DisplayName("GET /users requires JWT")
    void testGetUsers_whenMissingJWT_returns403() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity request = new HttpEntity(headers);

        // Act
        ResponseEntity<List<UserRest>> response = testRestTemplate.exchange("/users",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<UserRest>>() {
                });

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(),
                "HTTP Status Code 403 should have been returned");
    }

    @Test
    @Order(3)
    @DisplayName("/login works")
    void testUserLogin_whenValidCredentialsProvided_returnsJwtInAuthorizationHeader() throws JSONException {
        // Arrange
        JSONObject loginCredentials = new JSONObject();
        loginCredentials.put("email", "test@test.com");
        loginCredentials.put("password", "12345678");

        HttpEntity<String> request = new HttpEntity<>(loginCredentials.toString());

        // Act
        ResponseEntity response = testRestTemplate.postForEntity("/users/login", request, null);

        authorizationToken = response.getHeaders().getValuesAsList(SecurityConstants.HEADER_STRING).get(0);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "HTTP Status Code should be 200");
        assertNotNull(authorizationToken, "Response should contain Authorization header with JWT");
        assertNotNull(response.getHeaders().getValuesAsList("UserId").get(0),
                "Response should contain UserId header");
    }

    @Test
    @Order(4)
    @DisplayName("Get /users works")
    void testGetUsers_whenValidJwtProvided_returnsUsers() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(authorizationToken);

        HttpEntity request = new HttpEntity(headers);

        // Act
        ResponseEntity<List<UserRest>> response = testRestTemplate.exchange("/users",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<UserRest>>() {
                });

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "Http Status Code should be 200");
        assertTrue(response.getBody().size() == 1,
                "Should have exactly one user");
    }
}
