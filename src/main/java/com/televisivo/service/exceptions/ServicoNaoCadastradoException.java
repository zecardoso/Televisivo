package com.televisivo.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class ServicoNaoCadastradoException extends AuthenticationException {

    private static final long serialVersionUID = -1113521920226712900L;

    public ServicoNaoCadastradoException(String msg) {
        super(msg);
    }

    public ServicoNaoCadastradoException(Long id) {
        this(String.format("Não existe um cadastro do serviço com o código %d", id));
    }
}