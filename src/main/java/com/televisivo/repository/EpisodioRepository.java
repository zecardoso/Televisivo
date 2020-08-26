package com.televisivo.repository;

import com.televisivo.model.Episodio;
import com.televisivo.repository.query.EpisodioQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodioRepository extends JpaRepository<Episodio, Long>, EpisodioQuery {

    
}