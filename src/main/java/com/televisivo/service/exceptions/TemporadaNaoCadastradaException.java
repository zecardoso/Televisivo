package com.televisivo.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class TemporadaNaoCadastradaException extends AuthenticationException {

    private static final long serialVersionUID = -8105966840987384720L;

    public TemporadaNaoCadastradaException(String msg) {
        super(msg);
    }

    public TemporadaNaoCadastradaException(Long id) {
        this(String.format("Não existe um cadastro da temporada com o código %d", id));
    }
}