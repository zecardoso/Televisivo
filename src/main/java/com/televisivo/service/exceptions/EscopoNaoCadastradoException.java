package com.televisivo.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class EscopoNaoCadastradoException extends AuthenticationException {

    private static final long serialVersionUID = 775274825028750333L;

    public EscopoNaoCadastradoException(String msg) {
        super(msg);
    }

    public EscopoNaoCadastradoException(Long id) {
        this(String.format("Não existe um cadastro do escopo com o código %d", id));
    }
}