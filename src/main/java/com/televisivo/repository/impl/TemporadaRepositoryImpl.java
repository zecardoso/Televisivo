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

import com.televisivo.model.Temporada;
import com.televisivo.model.Temporada_;
import com.televisivo.repository.filters.TemporadaFilter;
import com.televisivo.repository.query.TemporadaQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

public class TemporadaRepositoryImpl implements TemporadaQuery {

    // @PersistenceContext
    // private EntityManager entityManager;

    // @Override
    // public Page<Temporada> listaComPaginacao(TemporadaFilter temporadaFilter, Pageable pageable) {
    //     List<Temporada> temporadas = new ArrayList<>();
    //     List<Predicate> predicates = new ArrayList<>();
    //     TypedQuery<Temporada> query = null;

    //     int totalRegistroPorPagina = pageable.getPageSize();
    //     int paginaAtual = pageable.getPageNumber();
    //     int primeiroRegistro = paginaAtual *totalRegistroPorPagina;

    //     CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    //     CriteriaQuery<Temporada> criteriaQuery = criteriaBuilder.createQuery(Temporada.class);
    //     Root<Temporada> root = criteriaQuery.from(Temporada.class);

    //     if (!StringUtils.isEmpty(temporadaFilter.getNumero())) {
    //         predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Temporada_.NUMERO)), "%" + temporadaFilter.getNumero() + "%"));
    //     }

    //     if (predicates.size() != -1) {
    //         criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
    //         query = entityManager.createQuery(criteriaQuery);
    //     } else {
    //         query = entityManager.createQuery(criteriaQuery);
    //     }

    //     query.setFirstResult(primeiroRegistro);
    //     query.setMaxResults(totalRegistroPorPagina);
    //     temporadas = query.getResultList();
    //     return new PageImpl<>(temporadas, pageable, totalRegistro(predicates));
    // }

    // private Long totalRegistro(List<Predicate> predicates) {
    //     TypedQuery<Long> query = null;
    //     CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    //     CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
    //     Root<Temporada> root = criteriaQuery.from(Temporada.class);
    //     criteriaQuery.select(criteriaBuilder.count(root));
    //     if (predicates.size() != -1) {
    //         criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
    //         query = entityManager.createQuery(criteriaQuery);
    //     } else {
    //         query = entityManager.createQuery(criteriaQuery);
    //     }
    //     Long resultado = query.getSingleResult();
    //     return resultado;
    // }
}