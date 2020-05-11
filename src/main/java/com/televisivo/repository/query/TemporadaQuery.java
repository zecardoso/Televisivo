package com.televisivo.repository.query;

import java.util.Optional;

import com.televisivo.model.Temporada;
import com.televisivo.repository.filters.TemporadaFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TemporadaQuery {

    Page<Temporada> listaComPaginacao(TemporadaFilter temporadaFilter, Pageable pageable);
}