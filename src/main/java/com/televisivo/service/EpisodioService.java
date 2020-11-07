package com.televisivo.service;

import com.televisivo.model.Episodio;
import com.televisivo.model.Temporada;

public interface EpisodioService extends GenericService<Episodio, Long>{

    Temporada findTemporadaByIdEpisodio(Long id);
    void atualizarQtdEpisodios(Temporada temporada);
}