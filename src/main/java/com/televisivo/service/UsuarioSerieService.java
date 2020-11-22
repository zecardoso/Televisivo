package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Episodio;
import com.televisivo.model.Serie;
import com.televisivo.model.UsuarioSerie;
import com.televisivo.security.UsuarioSistema;

public interface UsuarioSerieService {

    Long findUsuario(UsuarioSistema usuarioLogado);
    void atualizarQtdSeries(UsuarioSistema usuarioLogado);
    void atualizarQtdSeriesArq(UsuarioSistema usuarioLogado);
    List<Episodio> listaEpisodio(UsuarioSistema usuarioLogado);
    List<Serie> findAllSeries(UsuarioSistema usuarioLogado);
    List<Serie> findAllSeriesArq(UsuarioSistema usuarioLogado);

    int qtd(UsuarioSistema usuarioLogado);

    UsuarioSerie save(UsuarioSerie usuarioSerie);
    void salvar(UsuarioSistema usuarioLogado, Long serie);
    void arquivada(UsuarioSistema usuarioLogado, Long serie, Boolean arquivada);
    void remover(UsuarioSistema usuarioLogado, Long serie);
}