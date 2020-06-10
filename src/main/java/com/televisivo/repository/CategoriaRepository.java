package com.televisivo.repository;

import java.util.List;

import com.televisivo.model.Categoria;
import com.televisivo.repository.query.CategoriaQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>, CategoriaQuery {

    @Query("SELECT c FROM Categoria c WHERE c.nome like %:nome%")
    List<Categoria> buscarNome(@Param("nome") String nome);
}