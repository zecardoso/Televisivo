package com.televisivo.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class SenhaUsuarioDeveSerInformadaException extends AuthenticationException {

    private static final long serialVersionUID = 6221198195887495863L;

    public SenhaUsuarioDeveSerInformadaException(String msg) {
        super(msg);
    }
}
