package com.televisivo.repository.query;

import java.util.List;
import java.util.Optional;

import com.televisivo.model.Permission;
import com.televisivo.model.Usuario;
import com.televisivo.repository.filters.UsuarioFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioQuery {

    Page<Usuario> listaComPaginacao(UsuarioFilter usuarioFilter, Pageable pageable);
    Optional<Usuario> buscarAtivoPorEmail(String email);
    Optional<Usuario> findUsuarioByEmail(String email);
    Optional<Usuario> loginUsuarioByEmail(String email);
    List<Permission> findRolePermissaoByUsuarioId(Long id);
}