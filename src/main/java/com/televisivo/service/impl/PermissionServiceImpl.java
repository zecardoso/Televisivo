package com.televisivo.service.impl;

import java.util.List;

import com.televisivo.model.Permission;
import com.televisivo.model.Role;
import com.televisivo.model.RolePermissao;
import com.televisivo.model.Usuario;
import com.televisivo.repository.UsuarioRepository;
import com.televisivo.security.UsuarioSistema;
import com.televisivo.service.PermissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean hasPermission(Authentication usuarioLogado, Object permissao, Object escopo) {
        boolean toReturn = false;
        boolean true_permissao = false;
        boolean true_escopo = false;

        UsuarioSistema usuarioSistema = (UsuarioSistema) usuarioLogado.getPrincipal();
        List<Permission> lista = this.findRolePermissaoByUsuarioId(usuarioSistema.getUsuario().getId());
        for (Permission p : lista) {
            true_permissao = permissao.equals(p.getPermissao().toUpperCase()) ? true : false;
            true_escopo = escopo.equals(p.getEscopo().toUpperCase()) ? true : false;
            if (true_permissao == true && true_escopo == true) {
                toReturn = true;
                break;
            }
            true_permissao = false;
            true_escopo = false;
        }
        return toReturn;
    }

    @Override
    public List<Permission> findRolePermissaoByUsuarioId(Long id) {
        return usuarioRepository.findRolePermissaoByUsuarioId(id);
    }
}