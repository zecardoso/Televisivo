package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Categoria;
import com.televisivo.repository.filters.CategoriaFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaService {

    Categoria adicionar(Categoria categoria);
    Categoria alterar(Categoria categoria);
    void remover(Categoria categoria);
    Categoria buscarId(Long id);
    List<Categoria> buscarNome(String nome);
    List<Categoria> listar();
    Page<Categoria> listaComPaginacao(CategoriaFilter categoriaFilter, Pageable pageable);
}