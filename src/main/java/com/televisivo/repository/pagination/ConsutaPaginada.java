package com.televisivo.repository.pagination;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class ConsutaPaginada implements Pagination {

    private EntityManager entityManager;

    public ConsutaPaginada(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public <T> Page<T> listarDadosPaginados(Class<T> classe, Pageable pageable, String campo, String filtro) {
        TypedQuery<T> query = null;

        int totalRegistrosPorPaginas = pageable.getPageSize();
        int paginaAtual = pageable.getPageNumber();
        int primeiroRegistro = paginaAtual * totalRegistrosPorPaginas;

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(classe);
        Root<T> root = criteriaQuery.from(classe);

        root.fetch("roles");
        sortQuery(pageable, criteriaBuilder, criteriaQuery, root);
        Predicate predicate = predicados(criteriaBuilder, root, campo, filtro);
        if (!Objects.isNull(predicate)) {
            criteriaQuery.where(predicate);
        }

        query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(primeiroRegistro);
        query.setMaxResults(totalRegistrosPorPaginas);
        List<T> lista = query.getResultList();
        return new PageImpl<>(lista, pageable, totalRegistro(predicate, classe));
    }

    private <T> Predicate predicados(CriteriaBuilder criteriaBuilder, Root<T> root, String campo, String filtro) {
        Predicate predicates = null;
        if (!Objects.isNull(campo)) {
            predicates = criteriaBuilder.like(criteriaBuilder.lower(root.get(campo)), "%" + filtro + "%");
        }
        return predicates;
    }

    private <T> void sortQuery(Pageable pageable, CriteriaBuilder criteriaBuilder, CriteriaQuery<T> criteriaQuery, Root<T> root) {
        Sort sort = pageable.getSort();
        if (!sort.isUnsorted()) {
            Sort.Order order = sort.iterator().next();
            String propriedade = order.getProperty();
            criteriaQuery.orderBy(order.isAscending() ? criteriaBuilder.asc(root.get(propriedade)) : criteriaBuilder.desc(root.get(propriedade)));
        }
    }

    private <T> Long totalRegistro(Predicate predicates, Class<T> classe) {
        TypedQuery<Long> query = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> root = criteriaQuery.from(classe);
        criteriaQuery.select(criteriaBuilder.count(root));
        if (!Objects.isNull(predicates)) {
            criteriaQuery.where(predicates);
        }
        query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }
}