package com.televisivo.repository.query;

import com.televisivo.model.Permissao;
import com.televisivo.repository.filters.PermissaoFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PermissaoQuery {

    Page<Permissao> listaComPaginacao(PermissaoFilter permissaoFilter, Pageable pageable);
}