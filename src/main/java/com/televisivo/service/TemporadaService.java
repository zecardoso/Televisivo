package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Temporada;
import com.televisivo.repository.filters.TemporadaFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TemporadaService {

    Temporada adicionar(Temporada temporada);
    Temporada alterar(Temporada temporada);
    void remover(Temporada temporada);
    Temporada buscarId(Long id);
    List<Temporada> buscarNumero(int numero);
    List<Temporada> listar();
    Page<Temporada> listaComPaginacao(TemporadaFilter temporadaFilter, Pageable pageable);
    
    void salvarEpisodio(Temporada temporada);
    Temporada adicionarEpisodio(Temporada temporada);
    Temporada removerEpisodio(Temporada temporada, int index);
    Temporada buscarPorIdEpisodio(Long id);
}