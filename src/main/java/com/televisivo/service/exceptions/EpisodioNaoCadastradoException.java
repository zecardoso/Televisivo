package com.televisivo.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class EpisodioNaoCadastradoException extends AuthenticationException {

    private static final long serialVersionUID = 1561749226034059919L;

    public EpisodioNaoCadastradoException(String msg) {
        super(msg);
    }

    public EpisodioNaoCadastradoException(Long id) {
        this(String.format("Não existe um cadastro do episodio com o código %d", id));
    }
}