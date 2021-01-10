package com.televisivo.service.exceptions;

public class UsernameCadastradoException extends NegocioException {

    private static final long serialVersionUID = 9136809045086952180L;

    public UsernameCadastradoException(String message) {
        super(message);
    }
}