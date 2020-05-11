package com.televisivo.service.exceptions;

public class SenhaError extends RuntimeException {

    public SenhaError(String mensagem) {
        super(mensagem);
    }
}