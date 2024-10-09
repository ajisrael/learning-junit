package com.example.service;

import com.example.model.User;

public interface EmailVerificationService {
    void scheduleEmailConfirmation(User user);
}
