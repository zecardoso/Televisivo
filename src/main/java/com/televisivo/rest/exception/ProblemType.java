package com.televisivo.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProblemType {

    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "O recurso solicitado não foi localizado"),
	ENTIDADE_EM_USO("/entidade-em-uso", "A entidade está em uso no sistema"), 
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos");
	
	private String uri;
	private String title;
}