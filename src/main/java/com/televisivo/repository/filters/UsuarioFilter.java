package com.televisivo.repository.filters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioFilter {

    private String username;
    private String nome;
    private String sobrenome;
    private String email;
}