package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Temporada;

public interface TemporadaService extends GenericService<Temporada, Long>{

    List<Temporada> buscarNumero(int numero);
    
    void salvarEpisodio(Temporada temporada);
    Temporada adicionarEpisodio(Temporada temporada);
    Temporada removerEpisodio(Temporada temporada, int index);
    Temporada buscarPorIdEpisodio(Long id);
}