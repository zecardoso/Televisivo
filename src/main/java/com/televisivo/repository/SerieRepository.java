package com.televisivo.repository;

import java.util.List;

import com.televisivo.model.Serie;
import com.televisivo.repository.query.SerieQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long>, SerieQuery {

    @Query("SELECT s FROM Serie s WHERE s.nome like %:nome%")
    List<Serie> buscarNome(@Param("nome") String nome);

    @Query("SELECT s FROM Serie s JOIN Temporada t ON s.id = t.serie.id WHERE s.id = :id")
    Serie buscarPorIdTemporada(@Param("id") Long id);
}