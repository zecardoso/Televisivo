package com.televisivo.service.exceptions;

public class ElencoNaoCadastradoException extends EntidadeNaoCadastradaException {

    public ElencoNaoCadastradoException(String mensagem) {
        super(mensagem);
    }

    public ElencoNaoCadastradoException(Long id) {
        this(String.format("Não existe um cadastro do elenco com o código %d", id));
    }
}