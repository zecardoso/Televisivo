package com.televisivo.repository.query;

import com.televisivo.model.Role;
import com.televisivo.repository.filters.RoleFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleQuery {

    Page<Role> listaComPaginacao(RoleFilter roleFilter, Pageable pageable);
}