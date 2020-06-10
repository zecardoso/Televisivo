package com.televisivo.service.exceptions;

public class SenhaError extends RuntimeException {

    private static final long serialVersionUID = 6083837978190598703L;

    public SenhaError(String message) {
        super(message);
    }
}