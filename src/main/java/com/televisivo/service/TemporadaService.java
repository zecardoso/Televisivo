package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Temporada;
import com.televisivo.repository.filters.TemporadaFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TemporadaService extends GenericService<Temporada, Long>{

    List<Temporada> buscarNumero(int numero);
    Page<Temporada> listaComPaginacao(TemporadaFilter temporadaFilter, Pageable pageable);
    
    void salvarEpisodio(Temporada temporada);
    Temporada adicionarEpisodio(Temporada temporada);
    Temporada removerEpisodio(Temporada temporada, int index);
    Temporada buscarPorIdEpisodio(Long id);
}