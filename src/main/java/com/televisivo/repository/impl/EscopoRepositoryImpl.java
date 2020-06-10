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

import com.televisivo.model.Escopo;
import com.televisivo.model.Escopo_;
import com.televisivo.repository.filters.EscopoFilter;
import com.televisivo.repository.query.EscopoQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

public class EscopoRepositoryImpl implements EscopoQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Escopo> listaComPaginacao(EscopoFilter escopoFilter, Pageable pageable) {
        List<Predicate> predicates = new ArrayList<>();
        TypedQuery<Escopo> query = null;

        int totalRegistroPorPagina = pageable.getPageSize();
        int paginaAtual = pageable.getPageNumber();
        int primeiroRegistro = paginaAtual *totalRegistroPorPagina;

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Escopo> criteriaQuery = criteriaBuilder.createQuery(Escopo.class);
        Root<Escopo> root = criteriaQuery.from(Escopo.class);

        if (!StringUtils.isEmpty(escopoFilter.getNome())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Escopo_.NOME)), "%" + escopoFilter.getNome() + "%"));
        }

        if (predicates.size() != -1) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
            query = entityManager.createQuery(criteriaQuery);
        } else {
            query = entityManager.createQuery(criteriaQuery);
        }

        query.setFirstResult(primeiroRegistro);
        query.setMaxResults(totalRegistroPorPagina);
        List<Escopo> escopos = query.getResultList();
        return new PageImpl<>(escopos, pageable, totalRegistro(predicates));
    }

    private Long totalRegistro(List<Predicate> predicates) {
        TypedQuery<Long> query = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Escopo> root = criteriaQuery.from(Escopo.class);
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