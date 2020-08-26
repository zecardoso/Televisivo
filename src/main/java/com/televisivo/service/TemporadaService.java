package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Episodio;
import com.televisivo.model.Temporada;

public interface TemporadaService extends GenericService<Temporada, Long>{

    Long findTemporadaByIdEpisodio(Long id);
    Long findSerieByIdTemporada(Long id);
    List<Episodio> episodios(Long id);
    void atualizarQtdEpisodios(Long id);
    void atualizarQtdTemporadas(Long id);

    void salvarEpisodio(Temporada temporada);
    Temporada adicionarEpisodio(Temporada temporada);
    Temporada removerEpisodio(Temporada temporada, int index);
}