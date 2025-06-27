package com.application.util;

public class ErrorMessageEntity {
    private String message;
    public ErrorMessageEntity(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
