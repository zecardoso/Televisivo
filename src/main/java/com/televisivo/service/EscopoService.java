package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Escopo;
import com.televisivo.repository.filters.EscopoFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EscopoService extends GenericService<Escopo, Long> {

    List<Escopo> buscarNome(String nome);
    Page<Escopo> listaComPaginacao(EscopoFilter escopoFilter, Pageable pageable);
}