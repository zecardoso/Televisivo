package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Role;
import com.televisivo.repository.filters.RoleFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService extends GenericService<Role, Long> {

    List<Role> buscarNome(String nome);
    Page<Role> listaComPaginacao(RoleFilter roleFilter, Pageable pageable);
}