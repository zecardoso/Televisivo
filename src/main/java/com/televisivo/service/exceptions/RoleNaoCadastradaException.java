package com.televisivo.service.exceptions;

public class RoleNaoCadastradaException extends EntidadeNaoCadastradaException {

    public RoleNaoCadastradaException(String mensagem) {
        super(mensagem);
    }

    public RoleNaoCadastradaException(Long id) {
        this(String.format("Não existe um cadastro da role com o código %d", id));
    }
}