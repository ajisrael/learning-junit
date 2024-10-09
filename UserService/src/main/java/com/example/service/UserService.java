package com.example.service;

import com.example.model.User;

public interface UserService {
    public User createUser(String firstName,
                           String lastName,
                           String email,
                           String password,
                           String repeatPassword);
}
