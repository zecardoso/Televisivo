package com.televisivo.service.impl;

import java.util.List;

import com.televisivo.model.Episodio;
import com.televisivo.model.Usuario;
import com.televisivo.model.UsuarioEpisodio;
import com.televisivo.model.UsuarioEpisodioId;
import com.televisivo.repository.UsuarioEpisodioRepository;
import com.televisivo.repository.UsuarioRepository;
import com.televisivo.security.UsuarioSistema;
import com.televisivo.service.UsuarioEpisodioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioEpisodioServiceImpl implements UsuarioEpisodioService {

    @Autowired
    private UsuarioEpisodioRepository usuarioEpisodioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Long findUsuario(UsuarioSistema usuarioLogado) {
        return usuarioLogado.getUsuario().getId();
    }

    @Override
    public void marcar(UsuarioSistema usuarioLogado, Long episodioId) {
        UsuarioEpisodio usuarioEpisodio = new UsuarioEpisodio();
        UsuarioEpisodioId id = new UsuarioEpisodioId();
        id.setEpisodio(episodioId);
        id.setUsuario(findUsuario(usuarioLogado));
        usuarioEpisodio.setId(id);
        usuarioEpisodioRepository.save(usuarioEpisodio);
        atualizarQtdEpisodios(usuarioLogado);
    }

    @Override
    public void desmarcar(UsuarioSistema usuarioLogado, Long episodioId) {
        UsuarioEpisodioId id = new UsuarioEpisodioId();
        id.setEpisodio(episodioId);
        id.setUsuario(findUsuario(usuarioLogado));
        usuarioEpisodioRepository.deleteById(id);
        atualizarQtdEpisodios(usuarioLogado);
    }

    @Override
    public void atualizarQtdEpisodios(UsuarioSistema usuarioLogado) {
        Usuario usuario = usuarioRepository.getOne(findUsuario(usuarioLogado));
        usuario.setQtdEpisodios(usuarioEpisodioRepository.qtd(usuario.getId()));
    }

    @Override
    public List<Episodio> findAllEpisodios(UsuarioSistema usuarioLogado) {
        return usuarioEpisodioRepository.findAllEpisodios(findUsuario(usuarioLogado));
    }
}