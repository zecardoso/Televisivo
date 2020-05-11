package com.televisivo.service.exceptions;

public class EntidadeNaoCadastradaException extends NegocioException {

    public EntidadeNaoCadastradaException(String mensagem) {
        super(mensagem);
    }
}