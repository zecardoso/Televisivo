package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Serie;
import com.televisivo.model.Temporada;
import com.televisivo.repository.filters.SerieFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SerieService extends GenericService<Serie, Long> {

    List<Serie> buscarNome(String nome);
    Page<Serie> listaComPaginacao(SerieFilter serieFilter, Pageable pageable);

    Temporada findTemporadaByIdTemporada(Long id);
    List<Temporada> temporadas(Serie serie);
    void atualizarQtdTemporadas(Serie serie);

    void salvarTemporada(Serie serie);
    Serie adicionarTemporada(Serie serie);
    void removerTemporada(Temporada temporada);
}