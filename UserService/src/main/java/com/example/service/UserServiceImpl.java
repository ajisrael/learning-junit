package com.example.service;

import com.example.model.User;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public User createUser(String firstName,
                           String lastName,
                           String email,
                           String password,
                           String repeatPassword) {
        return new User(UUID.randomUUID().toString(), firstName, lastName, email);
    }
}
