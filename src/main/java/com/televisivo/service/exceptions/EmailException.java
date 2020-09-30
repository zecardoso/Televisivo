package com.televisivo.service.exceptions;

public class EmailException extends RuntimeException {

    private static final long serialVersionUID = 9194142944838512218L;

    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}