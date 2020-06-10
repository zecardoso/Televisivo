package com.televisivo.service;

import java.util.List;

import com.televisivo.model.RolePermissao;
import com.televisivo.model.RolePermissaoId;
import com.televisivo.repository.filters.RolePermissaoFilter;

public interface RolePermissaoService extends GenericService<RolePermissao, RolePermissaoId> {

	List<RolePermissao> findRolePermissaoEscopoFilter(RolePermissaoFilter rolePermissaoFilter);
}