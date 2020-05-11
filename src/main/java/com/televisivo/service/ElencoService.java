package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Elenco;
import com.televisivo.repository.filters.ElencoFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ElencoService {

    Elenco adicionar(Elenco elenco);
    Elenco alterar(Elenco elenco);
    void remover(Elenco elenco);
    Elenco buscarId(Long id);
    List<Elenco> buscarPersonagem(String personagem);
    List<Elenco> listar();
    Page<Elenco> listaComPaginacao(ElencoFilter elencoFilter, Pageable pageable);
}