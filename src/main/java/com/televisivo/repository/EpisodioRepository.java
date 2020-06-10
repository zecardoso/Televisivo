package com.televisivo.repository;

import java.util.List;

import com.televisivo.model.Episodio;
import com.televisivo.repository.query.EpisodioQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodioRepository extends JpaRepository<Episodio, Long>, EpisodioQuery {

    @Query("SELECT e FROM Episodio e WHERE e.numero like %:numero%")
    List<Episodio> buscarNumero(@Param("numero") int numero);
}