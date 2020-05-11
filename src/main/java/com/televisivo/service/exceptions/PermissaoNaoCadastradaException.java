package com.televisivo.service.exceptions;

public class PermissaoNaoCadastradaException extends EntidadeNaoCadastradaException {

    public PermissaoNaoCadastradaException(String mensagem) {
        super(mensagem);
    }

    public PermissaoNaoCadastradaException(Long id) {
        this(String.format("Não existe um cadastro da permissão com o código %d", id));
    }
}