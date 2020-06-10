package com.televisivo.rest.response;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErroCadastro {

    private boolean validated;
	private Map<String, String> erroMensagem = new HashMap<>();
	private String mensagem;
    private Long idClasse;
}