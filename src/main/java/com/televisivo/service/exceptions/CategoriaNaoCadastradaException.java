package com.televisivo.service.exceptions;

public class CategoriaNaoCadastradaException extends EntidadeNaoCadastradaException {

    public CategoriaNaoCadastradaException(String mensagem) {
        super(mensagem);
    }

    public CategoriaNaoCadastradaException(Long id) {
        this(String.format("Não existe um cadastro da categoria com o código %d", id));
    }
}