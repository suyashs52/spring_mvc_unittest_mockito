package com.demo.springboottest.exceptions;

public class UserServiceException extends RuntimeException{
    public UserServiceException(String message) {
        super(message);
    }
}
