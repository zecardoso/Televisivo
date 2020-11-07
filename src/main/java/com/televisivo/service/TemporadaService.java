package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Episodio;
import com.televisivo.model.Serie;
import com.televisivo.model.Temporada;

public interface TemporadaService extends GenericService<Temporada, Long>{

    Temporada findTemporadaByIdEpisodio(Long id);
    Serie findSerieByIdTemporada(Long id);
    List<Episodio> episodios(Long id);
    void atualizarQtdEpisodios(Temporada temporada);
    void atualizarQtdTemporadas(Serie serie);

    void salvarEpisodio(Temporada temporada);
    Temporada adicionarEpisodio(Temporada temporada);
    void removerEpisodio(Episodio episodio);
    Episodio findEpisodioByIdEpisodio(Long id);
    Temporada duplicateRow(Temporada temporada, Episodio episodio);
}