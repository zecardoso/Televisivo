package com.televisivo.service.exceptions;

public class EmailCadastradoException extends NegocioException {

    private static final long serialVersionUID = 7988509253293150400L;

    public EmailCadastradoException(String message) {
        super(message);
    }
}