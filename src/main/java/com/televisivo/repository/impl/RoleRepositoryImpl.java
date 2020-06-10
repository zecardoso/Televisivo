package com.televisivo.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.televisivo.model.Role;
import com.televisivo.model.Role_;
import com.televisivo.repository.filters.RoleFilter;
import com.televisivo.repository.query.RoleQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

public class RoleRepositoryImpl implements RoleQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Role> listaComPaginacao(RoleFilter roleFilter, Pageable pageable) {
        List<Predicate> predicates = new ArrayList<>();
        TypedQuery<Role> query = null;

        int totalRegistroPorPagina = pageable.getPageSize();
        int paginaAtual = pageable.getPageNumber();
        int primeiroRegistro = paginaAtual *totalRegistroPorPagina;

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
        Root<Role> root = criteriaQuery.from(Role.class);

        if (!StringUtils.isEmpty(roleFilter.getNome())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Role_.NOME)), "%" + roleFilter.getNome() + "%"));
        }

        if (predicates.size() != -1) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
            query = entityManager.createQuery(criteriaQuery);
        } else {
            query = entityManager.createQuery(criteriaQuery);
        }

        query.setFirstResult(primeiroRegistro);
        query.setMaxResults(totalRegistroPorPagina);
        List<Role> roles = query.getResultList();
        return new PageImpl<>(roles, pageable, totalRegistro(predicates));
    }

    private Long totalRegistro(List<Predicate> predicates) {
        TypedQuery<Long> query = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Role> root = criteriaQuery.from(Role.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        if (predicates.size() != -1) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
            query = entityManager.createQuery(criteriaQuery);
        } else {
            query = entityManager.createQuery(criteriaQuery);
        }
        return query.getSingleResult();
    }
}