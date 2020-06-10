package com.televisivo.repository;

import java.util.List;

import com.televisivo.model.Temporada;
import com.televisivo.repository.query.TemporadaQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporadaRepository extends JpaRepository<Temporada, Long>, TemporadaQuery {

    @Query("SELECT s FROM Temporada s WHERE s.numero like %:numero%")
    List<Temporada> buscarNumero(@Param("numero") int numero);

    @Query("SELECT t FROM Temporada t JOIN Episodio e ON t.id = e.temporada.id WHERE t.id = :id")
    Temporada buscarPorIdEpisodio(@Param("id") Long id);
}