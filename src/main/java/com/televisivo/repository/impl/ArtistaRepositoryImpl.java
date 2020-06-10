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

import com.televisivo.model.Artista;
import com.televisivo.model.Artista_;
import com.televisivo.repository.filters.ArtistaFilter;
import com.televisivo.repository.query.ArtistaQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

public class ArtistaRepositoryImpl implements ArtistaQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Artista> listaComPaginacao(ArtistaFilter artistaFilter, Pageable pageable) {
        List<Predicate> predicates = new ArrayList<>();
        TypedQuery<Artista> query = null;

        int totalRegistroPorPagina = pageable.getPageSize();
        int paginaAtual = pageable.getPageNumber();
        int primeiroRegistro = paginaAtual *totalRegistroPorPagina;

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Artista> criteriaQuery = criteriaBuilder.createQuery(Artista.class);
        Root<Artista> root = criteriaQuery.from(Artista.class);

        if (!StringUtils.isEmpty(artistaFilter.getNome())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Artista_.NOME)), "%" + artistaFilter.getNome() + "%"));
        }

        if (predicates.size() != -1) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
            query = entityManager.createQuery(criteriaQuery);
        } else {
            query = entityManager.createQuery(criteriaQuery);
        }

        query.setFirstResult(primeiroRegistro);
        query.setMaxResults(totalRegistroPorPagina);
        List<Artista> artistas = query.getResultList();
        return new PageImpl<>(artistas, pageable, totalRegistro(predicates));
    }

    private Long totalRegistro(List<Predicate> predicates) {
        TypedQuery<Long> query = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Artista> root = criteriaQuery.from(Artista.class);
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