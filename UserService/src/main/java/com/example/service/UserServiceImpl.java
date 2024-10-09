package com.example.service;

import com.example.data.UsersRepository;
import com.example.model.User;

import java.util.UUID;

import static com.example.constants.ExceptionMessages.FIRST_NAME_IS_EMPTY;
import static com.example.constants.ExceptionMessages.LAST_NAME_IS_EMPTY;

public class UserServiceImpl implements UserService {

    private UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public User createUser(String firstName,
                           String lastName,
                           String email,
                           String password,
                           String repeatPassword) {

        if (firstName == null || firstName.trim().length() == 0) {
            throw new IllegalArgumentException(FIRST_NAME_IS_EMPTY);
        }

        if (lastName == null || lastName.trim().length() == 0) {
            throw new IllegalArgumentException(LAST_NAME_IS_EMPTY);
        }

        User user = new User(UUID.randomUUID().toString(), firstName, lastName, email);

        boolean isUserCreated = usersRepository.save(user);

        if (!isUserCreated) throw new UserServiceException("Could not create user");

        return user;
    }
}
