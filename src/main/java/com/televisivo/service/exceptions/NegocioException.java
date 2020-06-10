package com.televisivo.service.exceptions;

public class NegocioException extends RuntimeException {

    private static final long serialVersionUID = 9194142944838512218L;

    public NegocioException(String message) {
        super(message);
    }
}