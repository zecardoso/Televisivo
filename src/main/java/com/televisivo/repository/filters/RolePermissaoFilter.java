package com.televisivo.repository.filters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolePermissaoFilter {

    private String role;
    private String permissao;
    private String escopo;
}