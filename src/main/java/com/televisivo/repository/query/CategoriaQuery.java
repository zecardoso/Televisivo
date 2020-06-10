package com.televisivo.repository.query;

import com.televisivo.model.Categoria;
import com.televisivo.repository.filters.CategoriaFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaQuery {

    Page<Categoria> listaComPaginacao(CategoriaFilter categoriaFilter, Pageable pageable);
}