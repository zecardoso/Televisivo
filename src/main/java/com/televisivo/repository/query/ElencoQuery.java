package com.televisivo.repository.query;

import com.televisivo.model.Elenco;
import com.televisivo.repository.filters.ElencoFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ElencoQuery {

    Page<Elenco> listaComPaginacao(ElencoFilter elencoFilter, Pageable pageable);
}