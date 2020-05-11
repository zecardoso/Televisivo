package com.televisivo.service.exceptions;

public class TemporadaNaoCadastradaException extends EntidadeNaoCadastradaException {

    public TemporadaNaoCadastradaException(String mensagem) {
        super(mensagem);
    }

    public TemporadaNaoCadastradaException(Long id) {
        this(String.format("Não existe um cadastro da temporada com o código %d", id));
    }
}