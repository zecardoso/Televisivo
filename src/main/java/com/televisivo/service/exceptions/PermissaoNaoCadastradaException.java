package com.televisivo.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class PermissaoNaoCadastradaException extends AuthenticationException {

    private static final long serialVersionUID = 8210337502325233287L;

    public PermissaoNaoCadastradaException(String msg) {
        super(msg);
    }

    public PermissaoNaoCadastradaException(Long id) {
        this(String.format("Não existe um cadastro da permissão com o código %d", id));
    }
}