package org.paulnikepro.hw3.exception;

public class UserServiceException extends IllegalArgumentException {
    private final String code;

    public UserServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
