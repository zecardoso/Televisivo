package com.televisivo.model.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Genero {

    MASCULINO("Masculino"),
    FEMININO("Feminino");
    
    private String descricao;
}