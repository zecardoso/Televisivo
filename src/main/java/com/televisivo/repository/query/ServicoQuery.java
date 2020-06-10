package com.televisivo.repository.query;

import com.televisivo.model.Servico;
import com.televisivo.repository.filters.ServicoFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServicoQuery {

    Page<Servico> listaComPaginacao(ServicoFilter servicoFilter, Pageable pageable);
}