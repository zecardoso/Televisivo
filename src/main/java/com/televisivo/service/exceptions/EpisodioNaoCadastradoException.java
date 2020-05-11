package com.televisivo.service.exceptions;

public class EpisodioNaoCadastradoException extends EntidadeNaoCadastradaException {

    public EpisodioNaoCadastradoException(String mensagem) {
        super(mensagem);
    }

    public EpisodioNaoCadastradoException(Long id) {
        this(String.format("Não existe um cadastro do episodio com o código %d", id));
    }
}