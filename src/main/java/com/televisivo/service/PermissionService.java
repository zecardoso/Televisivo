package com.televisivo.service;

import com.televisivo.model.Usuario;

import org.springframework.security.core.Authentication;

public interface PermissionService {

    boolean hasPermission(Authentication usuarioLogado, Object permissao, Object escopo);
    Usuario findRolePermissaoByUsuarioId(Long id);
}
