package com.televisivo.util.dto;

import lombok.Data;

@Data
public class AlterarSenha {
    
    private String newPassword;
    private String confirmePassword;
}
