package com.televisivo.repository.query;

import com.televisivo.model.Artista;
import com.televisivo.repository.filters.ArtistaFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArtistaQuery {

    Page<Artista> listaComPaginacao(ArtistaFilter artistaFilter, Pageable pageable);
}