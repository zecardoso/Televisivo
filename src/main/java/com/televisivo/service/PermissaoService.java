package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Permissao;
import com.televisivo.repository.filters.PermissaoFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PermissaoService extends GenericService<Permissao, Long> {

    List<Permissao> buscarNome(String nome);
    Page<Permissao> listaComPaginacao(PermissaoFilter permissaoFilter, Pageable pageable);
}