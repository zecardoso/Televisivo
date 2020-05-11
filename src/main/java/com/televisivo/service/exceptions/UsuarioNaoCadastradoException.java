package com.televisivo.service.exceptions;

public class UsuarioNaoCadastradoException extends EntidadeNaoCadastradaException {

    public UsuarioNaoCadastradoException(String mensagem) {
        super(mensagem);
    }

    public UsuarioNaoCadastradoException(Long id) {
        this(String.format("Não existe um cadastro do usuario com o código %d", id));
    }
}