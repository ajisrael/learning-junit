package com.appsdeveloperblog.tutorials.junit.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UsersRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UsersRepository usersRepository;

    private UserEntity userEntity;
    private UserEntity secondUserEntity;

    @BeforeEach
    void setup() {
        userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setFirstName("Sergey");
        userEntity.setLastName("Kargopolov");
        userEntity.setEmail("test@test.com");
        userEntity.setEncryptedPassword("12345678");

        testEntityManager.persistAndFlush(userEntity);

        secondUserEntity = new UserEntity();
        secondUserEntity.setUserId(UUID.randomUUID().toString());
        secondUserEntity.setFirstName("John");
        secondUserEntity.setLastName("Doe");
        secondUserEntity.setEmail("test2@test.com");
        secondUserEntity.setEncryptedPassword("12345678");

        testEntityManager.persistAndFlush(secondUserEntity);
    }

    @Test
    void testFindByEmail_whenGivenCorrectEmail_returnsUserEntity() {
        // Arrange

        // Act
        UserEntity storedUser = usersRepository.findByEmail(userEntity.getEmail());

        // Assert
        assertEquals(userEntity.getEmail(), storedUser.getEmail(),
                "Returned email address does not match expected value");
    }

    @Test
    void testFindByUserId_whenGivenCorrectUserId_returnsUserEntity() {
        // Arrange

        // Act
        UserEntity storedUser = usersRepository.findByUserId(userEntity.getUserId());

        // Assert
        assertNotNull(storedUser, "UserEntity should not be null");
        assertEquals(userEntity.getUserId(), storedUser.getUserId(),
                "Returned user id does not match expected value");
    }

    @Test
    void testFindUsersWithEmailEndingWith_whenGivenEmailDomain_returnsUsersWithGivenDomain() {
        // Arrange
        UserEntity thirdUserEntity = new UserEntity();
        thirdUserEntity.setUserId(UUID.randomUUID().toString());
        thirdUserEntity.setFirstName("Jane");
        thirdUserEntity.setLastName("Doe");
        thirdUserEntity.setEmail("test@gmail.com");
        thirdUserEntity.setEncryptedPassword("12345678");

        testEntityManager.persistAndFlush(thirdUserEntity);

        String emailDomain = "@gmail.com";

        // Act
        List<UserEntity> users = usersRepository.findUsersWithEmailEndingWith(emailDomain);

        // Assert
        assertEquals(1, users.size(), "There should be only one user in the list");
        assertTrue(users.get(0).getEmail().endsWith(emailDomain));
    }
}
