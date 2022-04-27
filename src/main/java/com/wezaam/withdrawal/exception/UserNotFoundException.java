package com.wezaam.withdrawal.exception;

public class UserNotFoundException extends RuntimeException {

    private String userId;

    public UserNotFoundException(String message, String userId) {
        super(message);
        this.userId = userId;
    }

    public UserNotFoundException(String message, Throwable error, String userId) {
        super(message, error);
        this.userId = userId;
    }

    public UserNotFoundException(Throwable e, String userId) {
        super(e);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
