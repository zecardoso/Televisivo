package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Episodio;
import com.televisivo.security.UsuarioSistema;

public interface UsuarioEpisodioService {

    Long findUsuario(UsuarioSistema usuarioLogado);
    void marcar(UsuarioSistema usuarioLogado, Long episodio);
    void desmarcar(UsuarioSistema usuarioLogado, Long episodio);
    void atualizarQtdEpisodios(UsuarioSistema usuarioLogado);
    List<Episodio> findAllEpisodios(UsuarioSistema usuarioLogado);
}