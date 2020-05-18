package com.televisivo.security;

import java.io.Serializable;

import com.televisivo.service.PermissionService;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomPermission implements PermissionEvaluator {

    private PermissionService permissionService;

    public CustomPermission(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }
}