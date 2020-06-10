package com.televisivo.repository.query;

import com.televisivo.model.Escopo;
import com.televisivo.repository.filters.EscopoFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EscopoQuery {

    Page<Escopo> listaComPaginacao(EscopoFilter escopoFilter, Pageable pageable);
}