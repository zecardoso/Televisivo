package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Elenco;
import com.televisivo.repository.filters.ElencoFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ElencoService extends GenericService<Elenco, Long>{

    List<Elenco> buscarPersonagem(String personagem);
    Page<Elenco> listaComPaginacao(ElencoFilter elencoFilter, Pageable pageable);
}