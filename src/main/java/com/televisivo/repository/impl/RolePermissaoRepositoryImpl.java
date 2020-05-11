package com.televisivo.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import com.televisivo.model.Escopo;
import com.televisivo.model.Permissao;
import com.televisivo.model.Role;
import com.televisivo.model.RolePermissao;
import com.televisivo.repository.filters.RolePermissaoFilter;
import com.televisivo.repository.query.RolePermissaoQuary;

import org.springframework.util.StringUtils;

public class RolePermissaoRepositoryImpl implements RolePermissaoQuary {

    @PersistenceContext
    private EntityManager entityManager;

	@Override
	public List<RolePermissao> buscarRolePermissaoFilter(RolePermissaoFilter rolePermissaoFilter) {
        TypedQuery<RolePermissao> query = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RolePermissao> criteriaQuery = criteriaBuilder.createQuery(RolePermissao.class);
        Root<RolePermissao> root = criteriaQuery.from(RolePermissao.class);
        criteriaQuery.select(root);
        query = entityManager.createQuery(criteriaQuery);
        if (!StringUtils.isEmpty(rolePermissaoFilter.getRoleNome())) {
            Join<RolePermissao, Role> rolePemrissao_role = root.join("role");
            criteriaQuery.where(criteriaBuilder.like(criteriaBuilder.lower(rolePemrissao_role.get("nome")), "%" + rolePermissaoFilter.getRoleNome()));
        } else if (!StringUtils.isEmpty(rolePermissaoFilter.getRoleNome())) {
            Join<RolePermissao, Permissao> rolePemrissao_permissao = root.join("permissao");
            criteriaQuery.where(criteriaBuilder.like(criteriaBuilder.lower(rolePemrissao_permissao.get("nome")), "%" + rolePermissaoFilter.getPermissaoNome()));
        } else if (!StringUtils.isEmpty(rolePermissaoFilter.getRoleNome())) {
            Join<RolePermissao, Escopo> rolePemrissao_escopo = root.join("escopo");
            criteriaQuery.where(criteriaBuilder.like(criteriaBuilder.lower(rolePemrissao_escopo.get("nome")), "%" + rolePermissaoFilter.getEscopoNome()));
        } else {
            criteriaQuery.select(root);
        }
		return query.getResultList();
	}
}