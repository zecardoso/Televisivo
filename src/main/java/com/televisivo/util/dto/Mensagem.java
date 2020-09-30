package com.televisivo.util.dto;

import java.util.Map;
import java.util.Set;

import lombok.Data;

@Data
public class Mensagem {

    private Set<String> destinatarios;
    private String assunto;
    private String corpo;
    private Map<String, Object> variaveis;
}
