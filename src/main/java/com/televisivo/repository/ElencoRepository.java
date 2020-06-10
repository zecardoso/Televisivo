package com.televisivo.repository;

import java.util.List;

import com.televisivo.model.Elenco;
import com.televisivo.repository.query.ElencoQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ElencoRepository extends JpaRepository<Elenco, Long>, ElencoQuery {

    @Query("SELECT e FROM Elenco e WHERE e.personagem like %:personagem%")
    List<Elenco> buscarPersonagem(@Param("personagem") String personagem);
}