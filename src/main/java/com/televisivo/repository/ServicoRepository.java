package com.televisivo.repository;

import java.util.List;

import com.televisivo.model.Servico;
import com.televisivo.repository.query.ServicoQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long>, ServicoQuery {

    @Query("SELECT s FROM Servico s WHERE s.nome like %:nome%")
    List<Servico> buscarNome(@Param("nome") String nome);
}