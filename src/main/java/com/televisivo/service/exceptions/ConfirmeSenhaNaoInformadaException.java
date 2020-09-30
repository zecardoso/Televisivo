package com.televisivo.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class ConfirmeSenhaNaoInformadaException extends AuthenticationException {

    private static final long serialVersionUID = -7898319409117005357L;

    public ConfirmeSenhaNaoInformadaException(String msg) {
        super(msg);
    }
}
