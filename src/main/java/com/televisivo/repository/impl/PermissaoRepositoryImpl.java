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

import com.televisivo.model.Permissao;
import com.televisivo.model.Permissao_;
import com.televisivo.repository.filters.PermissaoFilter;
import com.televisivo.repository.query.PermissaoQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

public class PermissaoRepositoryImpl implements PermissaoQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Permissao> listaComPaginacao(PermissaoFilter permissaoFilter, Pageable pageable) {
        List<Predicate> predicates = new ArrayList<>();
        TypedQuery<Permissao> query = null;

        int totalRegistroPorPagina = pageable.getPageSize();
        int paginaAtual = pageable.getPageNumber();
        int primeiroRegistro = paginaAtual *totalRegistroPorPagina;

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Permissao> criteriaQuery = criteriaBuilder.createQuery(Permissao.class);
        Root<Permissao> root = criteriaQuery.from(Permissao.class);

        if (!StringUtils.isEmpty(permissaoFilter.getNome())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Permissao_.NOME)), "%" + permissaoFilter.getNome() + "%"));
        }

        if (predicates.size() != -1) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
            query = entityManager.createQuery(criteriaQuery);
        } else {
            query = entityManager.createQuery(criteriaQuery);
        }

        query.setFirstResult(primeiroRegistro);
        query.setMaxResults(totalRegistroPorPagina);
        List<Permissao> permissaos = query.getResultList();
        return new PageImpl<>(permissaos, pageable, totalRegistro(predicates));
    }

    private Long totalRegistro(List<Predicate> predicates) {
        TypedQuery<Long> query = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Permissao> root = criteriaQuery.from(Permissao.class);
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