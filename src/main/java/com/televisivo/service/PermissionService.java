package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Permission;

import org.springframework.security.core.Authentication;

public interface PermissionService {

    boolean hasPermission(Authentication usuarioLogado, Object permissao, Object escopo);
    List<Permission> findRolePermissaoByUsuarioId(Long id);
}