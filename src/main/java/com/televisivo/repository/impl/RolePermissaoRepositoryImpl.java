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
	public List<RolePermissao> findRolePermissaoEscopoFilter(RolePermissaoFilter rolePermissaoFilter) {
        TypedQuery<RolePermissao> query = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RolePermissao> criteriaQuery = criteriaBuilder.createQuery(RolePermissao.class);
        Root<RolePermissao> root = criteriaQuery.from(RolePermissao.class);
        
        if (!StringUtils.isEmpty(rolePermissaoFilter.getRole())) {
            Join<RolePermissao, Role> role = root.join("role");
            criteriaQuery.where(criteriaBuilder.like(criteriaBuilder.lower(role.get("nome")), "%" + rolePermissaoFilter.getRole() + "%"));
        } else if (!StringUtils.isEmpty(rolePermissaoFilter.getPermissao())) {
            Join<RolePermissao, Permissao> permissao = root.join("permissao");
            criteriaQuery.where(criteriaBuilder.like(criteriaBuilder.lower(permissao.get("nome")), "%" + rolePermissaoFilter.getPermissao() + "%"));
        } else if (!StringUtils.isEmpty(rolePermissaoFilter.getEscopo())) {
            Join<RolePermissao, Escopo> escopo = root.join("escopo");
            criteriaQuery.where(criteriaBuilder.like(criteriaBuilder.lower(escopo.get("nome")), "%" + rolePermissaoFilter.getEscopo() + "%"));
        } else {
            criteriaQuery.select(root);
        }
        query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
}