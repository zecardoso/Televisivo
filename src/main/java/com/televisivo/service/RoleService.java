package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Role;
import com.televisivo.repository.filters.RoleFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {

    Role adicionar(Role role);
    Role alterar(Role role);
    void remover(Role role);
    Role buscarId(Long id);
    List<Role> buscarNome(String nome);
    List<Role> listar();
    Page<Role> listaComPaginacao(RoleFilter roleFilter, Pageable pageable);
}