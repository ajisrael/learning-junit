package com.example.service;

import com.example.model.User;

import java.util.UUID;

import static com.example.constants.ExceptionMessages.FIRST_NAME_IS_EMPTY;

public class UserServiceImpl implements UserService {
    @Override
    public User createUser(String firstName,
                           String lastName,
                           String email,
                           String password,
                           String repeatPassword) {

        if (firstName == null || firstName.trim().length() == 0) {
            throw new IllegalArgumentException(FIRST_NAME_IS_EMPTY);
        }

        return new User(UUID.randomUUID().toString(), firstName, lastName, email);
    }
}
