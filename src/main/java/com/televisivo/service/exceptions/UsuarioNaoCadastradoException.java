package com.televisivo.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UsuarioNaoCadastradoException extends AuthenticationException {

    private static final long serialVersionUID = -92913979534350020L;

    public UsuarioNaoCadastradoException(String msg) {
        super(msg);
    }

    public UsuarioNaoCadastradoException(Long id) {
        this(String.format("Não existe um cadastro do usuario com o código %d", id));
    }
}