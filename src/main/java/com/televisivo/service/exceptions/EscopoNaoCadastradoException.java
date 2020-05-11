package com.televisivo.service.exceptions;

public class EscopoNaoCadastradoException extends EntidadeNaoCadastradaException {

    public EscopoNaoCadastradoException(String mensagem) {
        super(mensagem);
    }

    public EscopoNaoCadastradoException(Long id) {
        this(String.format("Não existe um cadastro do escopo com o código %d", id));
    }
}