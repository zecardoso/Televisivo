package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Servico;
import com.televisivo.repository.filters.ServicoFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServicoService extends GenericService<Servico, Long> {

    List<Servico> buscarNome(String nome);
    Page<Servico> listaComPaginacao(ServicoFilter servicoFilter, Pageable pageable);
}