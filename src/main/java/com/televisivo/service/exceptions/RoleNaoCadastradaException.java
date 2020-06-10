package com.televisivo.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class RoleNaoCadastradaException extends AuthenticationException {

    private static final long serialVersionUID = 3380396990637046384L;

    public RoleNaoCadastradaException(String msg) {
        super(msg);
    }

    public RoleNaoCadastradaException(Long id) {
        this(String.format("Não existe um cadastro da role com o código %d", id));
    }
}