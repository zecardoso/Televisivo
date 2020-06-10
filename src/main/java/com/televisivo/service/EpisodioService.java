package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Episodio;

public interface EpisodioService extends GenericService<Episodio, Long>{

    List<Episodio> buscarNumero(int numero);
    
    // void salvarElenco(Episodio episodio);
    // Episodio adicionarElenco(Episodio episodio);
    // Episodio removerElenco(Episodio episodio, int index);
    // Episodio buscarPorIdElenco(Long id);
}