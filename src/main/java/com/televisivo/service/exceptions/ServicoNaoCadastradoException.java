package com.televisivo.service.exceptions;

public class ServicoNaoCadastradoException extends EntidadeNaoCadastradaException {

    public ServicoNaoCadastradoException(String mensagem) {
        super(mensagem);
    }

    public ServicoNaoCadastradoException(Long id) {
        this(String.format("Não existe um cadastro do serviço com o código %d", id));
    }
}