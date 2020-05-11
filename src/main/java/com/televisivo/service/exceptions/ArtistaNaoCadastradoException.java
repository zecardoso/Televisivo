package com.televisivo.service.exceptions;

public class ArtistaNaoCadastradoException extends EntidadeNaoCadastradaException {

    public ArtistaNaoCadastradoException(String mensagem) {
        super(mensagem);
    }

    public ArtistaNaoCadastradoException(Long id) {
        this(String.format("Não existe um cadastro do artista com o código %d", id));
    }
}