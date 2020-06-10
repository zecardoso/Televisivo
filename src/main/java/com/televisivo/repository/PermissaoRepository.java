package com.televisivo.repository;

import java.util.List;

import com.televisivo.model.Permissao;
import com.televisivo.repository.query.PermissaoQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long>, PermissaoQuery {

    @Query("SELECT p FROM Permissao p WHERE p.nome like %:nome%")
    List<Permissao> buscarNome(@Param("nome") String nome);
}