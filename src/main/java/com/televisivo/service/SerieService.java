package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Serie;
import com.televisivo.repository.filters.SerieFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SerieService extends GenericService<Serie, Long> {

    List<Serie> buscarNome(String nome);
    Page<Serie> listaComPaginacao(SerieFilter serieFilter, Pageable pageable);

    void salvarTemporada(Serie serie);
    Serie adicionarTemporada(Serie serie);
    Serie removerTemporada(Serie serie, int index);
    Serie buscarPorIdTemporada(Long id);

    // void salvarElenco(Serie serie);
    // Serie adicionarElenco(Serie serie);
    // Serie removerElenco(Serie serie, int index);
    // Serie buscarPorIdElenco(Long id);

    // void salvarArtista(Serie serie);
    // Serie adicionarArtista(Serie serie);
    // Serie removerArtista(Serie serie, int index);
    // Serie buscarPorIdArtista(Long id);
}