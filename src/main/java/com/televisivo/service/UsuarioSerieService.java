package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Categoria;
import com.televisivo.model.Episodio;
import com.televisivo.model.Serie;
import com.televisivo.model.Servico;
import com.televisivo.model.UsuarioSerie;
import com.televisivo.repository.filters.SerieFilter;
import com.televisivo.security.UsuarioSistema;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioSerieService {

    Long findUsuario(UsuarioSistema usuarioLogado);
    void atualizarQtdSeries(UsuarioSistema usuarioLogado);
    void atualizarQtdSeriesArq(UsuarioSistema usuarioLogado);
    void atualizarQtdSeguidores(Long serie);
    List<Serie> findAllSeries(UsuarioSistema usuarioLogado, Boolean arquivada);

	List<Serie> findAllSeriesBy(List<Serie> lista, UsuarioSistema usuarioLogado);
    List<Categoria> findAllCategorias();
    List<Servico> findAllServicos();

    int qtd(UsuarioSistema usuarioLogado);

    UsuarioSerie save(UsuarioSerie usuarioSerie);
    void salvar(UsuarioSistema usuarioLogado, Long serie);
    void arquivada(UsuarioSistema usuarioLogado, Long serie, Boolean arquivada);
    void remover(UsuarioSistema usuarioLogado, Long serie);

    List<Episodio> episodios(UsuarioSistema usuarioLogado, Long temporada);
    Page<Serie> listaComPaginacao(SerieFilter serieFilter, Pageable pageable, UsuarioSistema usuarioLogado);
}