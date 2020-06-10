package com.televisivo.service.exceptions;

public class EntidadeEmUsoException extends NegocioException {

    private static final long serialVersionUID = 2966969127517422881L;

    public EntidadeEmUsoException(String message) {
        super(message);
    }
}