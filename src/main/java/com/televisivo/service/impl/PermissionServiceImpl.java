package com.televisivo.service.impl;

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
        Usuario usuario = this.findRolePermissaoByUsuarioId(usuarioSistema.getUsuario().getId());
        for (Role role : usuario.getRoles()) {
            for (RolePermissao rolePermissao : role.getRolePermissoes()) {
                true_permissao = permissao.equals(rolePermissao.getPermissao().getNome().toUpperCase()) ? true : false;
                true_escopo = escopo.equals(rolePermissao.getEscopo().getNome().toUpperCase()) ? true : false;
                if (true_permissao && true_escopo) {
                    toReturn = true;
                    break;
                }
            }
            if (toReturn == true) {
                break;
            }
        }
        return toReturn;
    }

    @Override
    public Usuario findRolePermissaoByUsuarioId(Long id) {
        return usuarioRepository.findRolePermissaoByUsuarioId(id);
    }
}