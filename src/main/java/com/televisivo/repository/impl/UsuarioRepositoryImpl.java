package com.televisivo.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.televisivo.model.Permission;
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
        List<Usuario> usuarios = query.getResultList();
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
        return query.getSingleResult();
    }

    @Override
    public Optional<Usuario> buscarAtivoPorEmail(String email) {
        boolean ativo = Boolean.TRUE;
		TypedQuery<Usuario> query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.email =:email AND u.ativo =:ativo", Usuario.class);
		return query.setParameter("email", email).setParameter("ativo", ativo).setMaxResults(1).getResultList().stream().findFirst();
    }

    @Override
    public Optional<Usuario> findUsuarioByEmail(String email) {
		TypedQuery<Usuario> query = entityManager.createQuery("SELECT u FROM Usuario u LEFT JOIN FETCH u.roles WHERE u.email =:email", Usuario.class);
		return query.setParameter("email", email).setMaxResults(1).getResultList().stream().findFirst();
    }

    @Override
    public Optional<Usuario> loginUsuarioByEmail(String email) {
		return findUsuarioByEmail(email);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Permission> findRolePermissaoByUsuarioId(Long id) {
        List<Permission> lista = new ArrayList<>();
        Query query = entityManager.createNativeQuery("select role.nome r_nome, permissao.nome p_nome, escopo.nome e_nome from usuario inner join usuario_role on usuario_role.usuario_id = usuario.usuario_id inner join role on role.role_id = usuario_role.role_id inner join role_permissao on role_permissao.role_id = role.role_id inner join permissao on role_permissao.permissao_id = permissao.permissao_id inner join escopo on role_permissao.escopo_id = escopo.escopo_id where usuario.usuario_id =:id").setParameter("id", id);
        List<Object[]> mylista = query.getResultList();
        for (int i = 0; i < mylista.size(); i++) {
            Permission permission = new Permission();
            permission.setRole(mylista.get(i)[0].toString());
            permission.setPermissao(mylista.get(i)[1].toString());
            permission.setEscopo(mylista.get(i)[2].toString());
            lista.add(permission);
        }
        return lista;
    }
}