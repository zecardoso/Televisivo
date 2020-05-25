package com.televisivo.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class SenhaUsuarioDeveSerInformadaException extends AuthenticationException {

    public SenhaUsuarioDeveSerInformadaException(String msg) {
        super(msg);
    }

}
