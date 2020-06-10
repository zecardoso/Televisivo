package com.televisivo.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class ElencoNaoCadastradoException extends AuthenticationException {

    private static final long serialVersionUID = -2205817950449449884L;

    public ElencoNaoCadastradoException(String msg) {
        super(msg);
    }

    public ElencoNaoCadastradoException(Long id) {
        this(String.format("Não existe um cadastro do elenco com o código %d", id));
    }
}