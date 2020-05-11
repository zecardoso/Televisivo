package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Permissao;
import com.televisivo.repository.filters.PermissaoFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PermissaoService {

    Permissao adicionar(Permissao permissao);
    Permissao alterar(Permissao permissao);
    void remover(Permissao permissao);
    Permissao buscarId(Long id);
    List<Permissao> buscarNome(String nome);
    List<Permissao> listar();
    Page<Permissao> listaComPaginacao(PermissaoFilter permissaoFilter, Pageable pageable);
}