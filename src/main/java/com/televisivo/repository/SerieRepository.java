package com.televisivo.repository;

import java.util.List;

import com.televisivo.model.Serie;
import com.televisivo.model.Temporada;
import com.televisivo.repository.query.SerieQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long>, SerieQuery {

    @Query("SELECT s FROM Serie s WHERE s.nome like %:nome%")
    List<Serie> buscarNome(@Param("nome") String nome);

    @Query("SELECT t FROM Temporada t WHERE t.serie.id = :id ORDER BY t.numero")
    List<Temporada> temporadas(@Param("id") Long id);
}