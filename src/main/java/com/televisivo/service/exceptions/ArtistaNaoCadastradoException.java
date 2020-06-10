package com.televisivo.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class ArtistaNaoCadastradoException extends AuthenticationException {

    private static final long serialVersionUID = 6978443023779074359L;

    public ArtistaNaoCadastradoException(String msg) {
        super(msg);
    }

    public ArtistaNaoCadastradoException(Long id) {
        this(String.format("Não existe um cadastro do artista com o código %d", id));
    }
}