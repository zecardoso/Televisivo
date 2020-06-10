package com.televisivo.repository.query;

import com.televisivo.model.Serie;
import com.televisivo.repository.filters.SerieFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SerieQuery {

    Page<Serie> listaComPaginacao(SerieFilter serieFilter, Pageable pageable);
}