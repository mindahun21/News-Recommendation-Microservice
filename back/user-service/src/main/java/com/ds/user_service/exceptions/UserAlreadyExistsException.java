package com.ds.user_service.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String format) {
        super(format);
    }
}
