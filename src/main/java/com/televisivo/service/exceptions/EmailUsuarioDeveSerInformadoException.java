package com.televisivo.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class EmailUsuarioDeveSerInformadoException extends AuthenticationException {

    private static final long serialVersionUID = -109696048162152405L;

    public EmailUsuarioDeveSerInformadoException(String msg) {
        super(msg);
    }

}
