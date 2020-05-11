package com.televisivo.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.televisivo.model.Usuario;
import com.televisivo.model.Usuario_;
import com.televisivo.repository.filters.UsuarioFilter;
import com.televisivo.repository.query.UsuarioQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

public class UsuarioRepositoryImpl implements UsuarioQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Usuario> listaComPaginacao(UsuarioFilter usuarioFilter, Pageable pageable) {
        List<Usuario> usuarios = new ArrayList<>();
        List<Predicate> predicates = new ArrayList<>();
        TypedQuery<Usuario> query = null;

        int totalRegistroPorPagina = pageable.getPageSize();
        int paginaAtual = pageable.getPageNumber();
        int primeiroRegistro = paginaAtual *totalRegistroPorPagina;

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        if (!StringUtils.isEmpty(usuarioFilter.getUsername())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Usuario_.USERNAME)), "%" + usuarioFilter.getUsername() + "%"));
        }
        
        if (!StringUtils.isEmpty(usuarioFilter.getNome())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Usuario_.NOME)), "%" + usuarioFilter.getNome() + "%"));
        }

        if (!StringUtils.isEmpty(usuarioFilter.getSobrenome())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Usuario_.SOBRENOME)), "%" + usuarioFilter.getSobrenome() + "%"));
        }

        if (!StringUtils.isEmpty(usuarioFilter.getEmail())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Usuario_.EMAIL)), "%" + usuarioFilter.getEmail() + "%"));
        }

        if (predicates.size() != -1) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
            query = entityManager.createQuery(criteriaQuery);
        } else {
            query = entityManager.createQuery(criteriaQuery);
        }

        query.setFirstResult(primeiroRegistro);
        query.setMaxResults(totalRegistroPorPagina);
        usuarios = query.getResultList();
        return new PageImpl<>(usuarios, pageable, totalRegistro(predicates));
    }

    private Long totalRegistro(List<Predicate> predicates) {
        TypedQuery<Long> query = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);
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

    @Override
    public Optional<Usuario> buscarAtivoPorEmail(String email) {
        TypedQuery<Usuario> query = entityManager.createQuery("SELECT u FROM Usuario u WHERE lower(u.email) = lower(:email) and u.ativo = true", Usuario.class);
        return query.setParameter("email", email).setMaxResults(1).getResultList().stream().findFirst();
    }
}