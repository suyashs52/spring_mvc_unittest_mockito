package com.demo.service;

import com.demo.model.User;

public interface EmailVerificationService {
    void scheduleEmailConfirmation(User user);
}
