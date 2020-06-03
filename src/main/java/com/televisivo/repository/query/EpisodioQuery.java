package com.televisivo.repository.query;

import java.util.Optional;

import com.televisivo.model.Episodio;
import com.televisivo.repository.filters.EpisodioFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EpisodioQuery {

    // Page<Episodio> listaComPaginacao(EpisodioFilter episodioFilter, Pageable pageable);
}