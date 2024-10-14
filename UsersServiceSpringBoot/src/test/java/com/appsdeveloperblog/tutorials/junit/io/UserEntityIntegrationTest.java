package com.appsdeveloperblog.tutorials.junit.io;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.userdetails.User;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserEntityIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void testUserEntity_whenValidUserDetailsProvided_shouldReturnStoredUserDetails() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setFirstName("Sergey");
        userEntity.setLastName("Kargopolov");
        userEntity.setEmail("test@test.com");
        userEntity.setEncryptedPassword("12345678");

        // Act
        UserEntity storedUserEntity = testEntityManager.persistAndFlush(userEntity);

        // Assert
        // We are using > 0 bc we don't know for sure this method will run first so we can't use == 1
        assertTrue(storedUserEntity.getId() > 0);
        assertEquals(userEntity.getUserId(), storedUserEntity.getUserId());
        assertEquals(userEntity.getFirstName(), storedUserEntity.getFirstName());
        assertEquals(userEntity.getLastName(), storedUserEntity.getLastName());
        assertEquals(userEntity.getEmail(), storedUserEntity.getEmail());
        assertEquals(userEntity.getEncryptedPassword(), storedUserEntity.getEncryptedPassword());
    }
}
