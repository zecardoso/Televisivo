package com.televisivo.service.impl;

import java.util.List;

import com.televisivo.model.Episodio;
import com.televisivo.model.Temporada;
import com.televisivo.repository.EpisodioRepository;
import com.televisivo.repository.TemporadaRepository;
import com.televisivo.repository.filters.TemporadaFilter;
import com.televisivo.service.TemporadaService;
import com.televisivo.service.exceptions.TemporadaNaoCadastradaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TemporadaServiceImpl implements TemporadaService {

    @Autowired
    private TemporadaRepository temporadaRepository;

    @Autowired
    private EpisodioRepository episodioRepository;

    @Override
    public Temporada adicionar(Temporada temporada) {
        return temporadaRepository.save(temporada);
    }

    @Override
    public Temporada alterar(Temporada temporada) {
        return temporadaRepository.save(temporada);
    }

    @Override
    public void remover(Temporada temporada) {
		try {
			temporadaRepository.deleteById(temporada.getId());
		} catch (EmptyResultDataAccessException e){
			throw new TemporadaNaoCadastradaException(String.format("O temporada com o código %d não foi encontrada!", temporada.getId()));
		}
    }

    @Override
    @Transactional(readOnly = true)
    public Temporada buscarId(Long id) {
        return temporadaRepository.findById(id).orElseThrow(() -> new TemporadaNaoCadastradaException(id));
    }

    @Override
    public List<Temporada> buscarNumero(int numero) {
        return temporadaRepository.buscarNumero(numero);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Temporada> listar() {
        return temporadaRepository.findAll();
    }

    @Override
    public Page<Temporada> listaComPaginacao(TemporadaFilter temporadaFilter, Pageable pageable) {
        return temporadaRepository.listaComPaginacao(temporadaFilter, pageable);
    }

    @Override
    public void salvarEpisodio(Temporada temporada) {
        if (temporada.getEpisodios().size() != -1) {
            for (Episodio episodio: temporada.getEpisodios()) {
                episodio.setTemporada(temporada);
                episodioRepository.save(episodio);
            }
        }
    }

    @Override
    public Temporada adicionarEpisodio(Temporada temporada) {
        Episodio episodio = new Episodio();
        episodio.setTemporada(temporada);
        temporada.getEpisodios().add(episodio);
        return temporada;
    }

    @Override
    public Temporada removerEpisodio(Temporada temporada, int index) {
        Episodio episodio = temporada.getEpisodios().get(index);
        if (episodio.getId() != null) {
            episodioRepository.deleteById(episodio.getId());
        }
        temporada.getEpisodios().remove(index);
        return null;
    }

    @Override
    public Temporada buscarPorIdEpisodio(Long id) {
        return temporadaRepository.buscarPorIdEpisodio(id);
    }
}