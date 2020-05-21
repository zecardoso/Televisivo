package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Categoria;
import com.televisivo.repository.filters.CategoriaFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaService extends GenericService<Categoria, Long> {

    List<Categoria> buscarNome(String nome);
    Page<Categoria> listaComPaginacao(CategoriaFilter categoriaFilter, Pageable pageable);
}