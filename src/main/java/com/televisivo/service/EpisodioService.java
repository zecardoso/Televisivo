package com.televisivo.service;

import com.televisivo.model.Episodio;

public interface EpisodioService extends GenericService<Episodio, Long>{

    Long findTemporadaByIdEpisodio(Long id);
    Long findSerieByIdEpisodio(Long id);
    void atualizarQtdEpisodios(Long id);
}