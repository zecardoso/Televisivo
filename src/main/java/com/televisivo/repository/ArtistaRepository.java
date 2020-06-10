package com.televisivo.repository;

import java.util.List;

import com.televisivo.model.Artista;
import com.televisivo.repository.query.ArtistaQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long>, ArtistaQuery {

    @Query("SELECT a FROM Artista a WHERE a.nome like %:nome%")
    List<Artista> buscarNome(@Param("nome") String nome);
}