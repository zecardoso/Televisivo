package com.televisivo.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class SerieNaoCadastradaException extends AuthenticationException {

    private static final long serialVersionUID = -5123981034962106278L;

    public SerieNaoCadastradaException(String msg) {
        super(msg);
    }

    public SerieNaoCadastradaException(Long id) {
        this(String.format("Não existe um cadastro da serie com o código %d", id));
    }
}