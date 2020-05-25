package com.televisivo.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class EmailUsuarioDeveSerInformadoException extends AuthenticationException {

    public EmailUsuarioDeveSerInformadoException(String msg) {
        super(msg);
    }

}
