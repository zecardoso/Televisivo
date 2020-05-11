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

import com.televisivo.model.Episodio;
import com.televisivo.model.Episodio_;
import com.televisivo.repository.filters.EpisodioFilter;
import com.televisivo.repository.query.EpisodioQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

public class EpisodioRepositoryImpl implements EpisodioQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Episodio> listaComPaginacao(EpisodioFilter episodioFilter, Pageable pageable) {
        List<Episodio> episodios = new ArrayList<>();
        List<Predicate> predicates = new ArrayList<>();
        TypedQuery<Episodio> query = null;

        int totalRegistroPorPagina = pageable.getPageSize();
        int paginaAtual = pageable.getPageNumber();
        int primeiroRegistro = paginaAtual *totalRegistroPorPagina;

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Episodio> criteriaQuery = criteriaBuilder.createQuery(Episodio.class);
        Root<Episodio> root = criteriaQuery.from(Episodio.class);

        if (!StringUtils.isEmpty(episodioFilter.getNumero())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Episodio_.NUMERO)), "%" + episodioFilter.getNumero() + "%"));
        }

        if (!StringUtils.isEmpty(episodioFilter.getNome())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Episodio_.NOME)), "%" + episodioFilter.getNome() + "%"));
        }

        if (predicates.size() != -1) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
            query = entityManager.createQuery(criteriaQuery);
        } else {
            query = entityManager.createQuery(criteriaQuery);
        }

        query.setFirstResult(primeiroRegistro);
        query.setMaxResults(totalRegistroPorPagina);
        episodios = query.getResultList();
        return new PageImpl<>(episodios, pageable, totalRegistro(predicates));
    }

    private Long totalRegistro(List<Predicate> predicates) {
        TypedQuery<Long> query = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Episodio> root = criteriaQuery.from(Episodio.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        if (predicates.size() != -1) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
            query = entityManager.createQuery(criteriaQuery);
        } else {
            query = entityManager.createQuery(criteriaQuery);
        }
        Long resultado = query.getSingleResult();
        return resultado;
    }
}