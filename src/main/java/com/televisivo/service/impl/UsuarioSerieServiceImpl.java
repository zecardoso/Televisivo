package com.televisivo.service.impl;

import java.util.List;

import com.televisivo.model.Episodio;
import com.televisivo.model.Serie;
import com.televisivo.model.Usuario;
import com.televisivo.model.UsuarioSerie;
import com.televisivo.model.UsuarioSerieId;
import com.televisivo.repository.SerieRepository;
import com.televisivo.repository.TemporadaRepository;
import com.televisivo.repository.UsuarioEpisodioRepository;
import com.televisivo.repository.UsuarioRepository;
import com.televisivo.repository.UsuarioSerieRepository;
import com.televisivo.repository.filters.SerieFilter;
import com.televisivo.security.UsuarioSistema;
import com.televisivo.service.UsuarioSerieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioSerieServiceImpl implements UsuarioSerieService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioSerieRepository usuarioSerieRepository;

    @Autowired
    private UsuarioEpisodioRepository usuarioEpisodioRepository;

    @Autowired
    private TemporadaRepository temporadaRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Override
    public Long findUsuario(UsuarioSistema usuarioLogado) {
        return usuarioLogado.getUsuario().getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Serie> findAllSeries(UsuarioSistema usuarioLogado, Boolean arquivada) {
        return usuarioSerieRepository.findAllSeries(findUsuario(usuarioLogado), arquivada);
    }

    @Override
    public void salvar(UsuarioSistema usuarioLogado, Long serie) {
        UsuarioSerie usuarioSerie = new UsuarioSerie();
        UsuarioSerieId id = new UsuarioSerieId();
        id.setSerie(serie);
        id.setUsuario(findUsuario(usuarioLogado));
        usuarioSerie.setId(id);
        usuarioSerieRepository.save(usuarioSerie);
        atualizarQtdSeries(usuarioLogado);
        atualizarQtdSeriesArq(usuarioLogado);
    }

    @Override
    public void arquivada(UsuarioSistema usuarioLogado, Long serie, Boolean arquivada) {
        UsuarioSerieId id = new UsuarioSerieId();
        id.setSerie(serie);
        id.setUsuario(findUsuario(usuarioLogado));
        UsuarioSerie usuarioSerie = usuarioSerieRepository.getOne(id);
        usuarioSerie.setArquivada(arquivada);
        atualizarQtdSeries(usuarioLogado);
        atualizarQtdSeriesArq(usuarioLogado);
    }

    @Override
    public void remover(UsuarioSistema usuarioLogado, Long serie) {
        UsuarioSerieId id = new UsuarioSerieId();
        id.setSerie(serie);
        id.setUsuario(findUsuario(usuarioLogado));
        usuarioSerieRepository.deleteById(id);
        atualizarQtdSeries(usuarioLogado);
        atualizarQtdSeriesArq(usuarioLogado);
    }

    @Override
    public void atualizarQtdSeries(UsuarioSistema usuarioLogado) {
        Usuario usuario = usuarioRepository.getOne(findUsuario(usuarioLogado));
        usuario.setQtdSeries(usuarioSerieRepository.qtd(usuario.getId(), false));
    }

    @Override
    public void atualizarQtdSeriesArq(UsuarioSistema usuarioLogado) {
        Usuario usuario = usuarioRepository.getOne(findUsuario(usuarioLogado));
        usuario.setQtdSeriesArq(usuarioSerieRepository.qtd(usuario.getId(), true));
    }

    @Override
    public UsuarioSerie save(UsuarioSerie usuarioSerie) {
        return usuarioSerieRepository.save(usuarioSerie);
    }

    @Override
    public int qtd(UsuarioSistema usuarioLogado) {
        return usuarioSerieRepository.qtd(findUsuario(usuarioLogado), false);
    }

    @Override
    public List<Episodio> episodios(UsuarioSistema usuarioLogado, Long temporada) {
        List<Episodio> lista = temporadaRepository.episodios(temporada);
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).setMarcado(usuarioEpisodioRepository.marcado(findUsuario(usuarioLogado), lista.get(i).getId()) != 0);
        }
        return lista;
    }

    @Override
    public Page<Serie> listaComPaginacao(SerieFilter serieFilter, Pageable pageable, UsuarioSistema usuarioLogado) {
        Page<Serie> lista = serieRepository.listaComPaginacao(serieFilter, pageable);
        for (int i = 0; i < lista.getContent().size(); i++) {
            lista.getContent().get(i).setSalva(usuarioSerieRepository.salva(findUsuario(usuarioLogado), lista.getContent().get(i).getId(), false) != 0);
            lista.getContent().get(i).setArquivada(usuarioSerieRepository.salva(findUsuario(usuarioLogado), lista.getContent().get(i).getId(), true) != 0);
        }
        return lista;
    }
}