package com.televisivo.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class CategoriaNaoCadastradaException extends AuthenticationException {

    private static final long serialVersionUID = -7003999074742680741L;

    public CategoriaNaoCadastradaException(String msg) {
        super(msg);
    }

    public CategoriaNaoCadastradaException(Long id) {
        this(String.format("Não existe um cadastro da categoria com o código %d", id));
    }
}