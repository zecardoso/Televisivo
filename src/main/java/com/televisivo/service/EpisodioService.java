package com.televisivo.service;

import com.televisivo.model.Episodio;

public interface EpisodioService extends GenericService<Episodio, Long>{

    Long findTemporadaByIdEpisodio(Long id);
    Long findSerieByIdEpisodio(Long id);
    void atualizarQtdEpisodios(Long id);
    
    // void salvarElenco(Episodio episodio);
    // Episodio adicionarElenco(Episodio episodio);
    // Episodio removerElenco(Episodio episodio, int index);
    // Episodio buscarPorIdElenco(Long id);
}