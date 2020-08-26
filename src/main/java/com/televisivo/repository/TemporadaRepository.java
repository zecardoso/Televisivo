package com.televisivo.repository;

import java.util.List;

import com.televisivo.model.Episodio;
import com.televisivo.model.Temporada;
import com.televisivo.repository.query.TemporadaQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporadaRepository extends JpaRepository<Temporada, Long>, TemporadaQuery {

    @Query("SELECT e FROM Episodio e WHERE e.temporada.id = :id ORDER BY e.numero")
	List<Episodio> episodios(@Param("id") Long id);
}