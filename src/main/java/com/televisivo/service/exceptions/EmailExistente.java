package com.televisivo.service.exceptions;

public class EmailExistente extends RuntimeException {

    public EmailExistente(String mensagem) {
        super(mensagem);
    }
}