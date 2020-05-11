package com.televisivo.service.exceptions;

public class SerieNaoCadastradaException extends EntidadeNaoCadastradaException {

    public SerieNaoCadastradaException(String mensagem) {
        super(mensagem);
    }

    public SerieNaoCadastradaException(Long id) {
        this(String.format("Não existe um cadastro da serie com o código %d", id));
    }
}