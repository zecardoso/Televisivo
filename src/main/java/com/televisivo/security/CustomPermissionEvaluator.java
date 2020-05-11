package com.televisivo.security;

import java.io.Serializable;

import com.televisivo.service.UsuarioService;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private UsuarioService usuarioService;

    public CustomPermissionEvaluator(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public boolean hasPermission(Authentication usuarioLogado, Object atividade, Object escopo) {
        hasPrevilege(usuarioLogado, atividade, escopo);
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }

    public boolean hasPrevilege(Authentication usuarioLogado, Object atividade, Object escopo) {
        return false;
    }
}