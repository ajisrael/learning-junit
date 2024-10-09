package com.example.constants;

public class ExceptionMessages {

    private ExceptionMessages() {
        throw new IllegalStateException("Constants class");
    }

    public static final String FIRST_NAME_IS_EMPTY = "User's first name cannot be null or empty";
    public static final String LAST_NAME_IS_EMPTY = "User's last name cannot be null or empty";
}
