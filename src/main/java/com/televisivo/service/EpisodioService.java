package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Episodio;
import com.televisivo.repository.filters.EpisodioFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EpisodioService extends GenericService<Episodio, Long>{

    List<Episodio> buscarNumero(int numero);
    // Page<Episodio> listaComPaginacao(EpisodioFilter episodioFilter, Pageable pageable);

    // void salvarElenco(Episodio episodio);
    // Episodio adicionarElenco(Episodio episodio);
    // Episodio removerElenco(Episodio episodio, int index);
    // Episodio buscarPorIdElenco(Long id);
}