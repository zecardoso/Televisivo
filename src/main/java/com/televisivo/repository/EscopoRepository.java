package com.televisivo.repository;

import java.util.List;

import com.televisivo.model.Escopo;
import com.televisivo.repository.query.EscopoQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EscopoRepository extends JpaRepository<Escopo, Long>, EscopoQuery {

    @Query("SELECT e FROM Escopo e WHERE e.nome like %:nome%")
    List<Escopo> buscarNome(@Param("nome") String nome);
}