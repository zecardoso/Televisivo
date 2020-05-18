package com.televisivo.service.impl;

import com.televisivo.model.Usuario;
import com.televisivo.service.PermissionService;

import org.springframework.security.core.Authentication;

public class PermissionServiceImpl implements PermissionService {

    @Override
    public boolean hasPermission(Authentication usuarioLogado, Object permissao, Object escopo) {
        return false;
    }

    @Override
    public Usuario findRolePermissaoByUsuarioId(Long id) {
        return null;
    }
}