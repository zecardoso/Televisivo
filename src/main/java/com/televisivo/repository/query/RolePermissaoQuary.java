package com.televisivo.repository.query;

import java.util.List;

import com.televisivo.model.RolePermissao;
import com.televisivo.repository.filters.RolePermissaoFilter;

public interface RolePermissaoQuary {

	List<RolePermissao> findRolePermissaoEscopoFilter(RolePermissaoFilter rolePermissaoFilter);  
}