package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Artista;
import com.televisivo.repository.filters.ArtistaFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArtistaService extends GenericService<Artista, Long> {

    List<Artista> buscarNome(String nome);
    Page<Artista> listaComPaginacao(ArtistaFilter artistaFilter, Pageable pageable);

    // void salvarCategoria(Artista artista);
    // Artista adicionarCategoria(Artista artista);
    // Artista removerCategoria(Artista artista, int index);
    // Artista buscarPorIdCategoria(Long id);
}