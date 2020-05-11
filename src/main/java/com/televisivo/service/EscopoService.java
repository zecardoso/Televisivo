package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Escopo;
import com.televisivo.repository.filters.EscopoFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EscopoService {

    Escopo adicionar(Escopo escopo);
    Escopo alterar(Escopo escopo);
    void remover(Escopo escopo);
    Escopo buscarId(Long id);
    List<Escopo> buscarNome(String nome);
    List<Escopo> listar();
    Page<Escopo> listaComPaginacao(EscopoFilter escopoFilter, Pageable pageable);
}