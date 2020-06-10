package com.televisivo.service.impl;

import java.util.List;

import com.televisivo.model.Episodio;
import com.televisivo.model.Temporada;
import com.televisivo.repository.EpisodioRepository;
import com.televisivo.repository.TemporadaRepository;
import com.televisivo.service.TemporadaService;
import com.televisivo.service.exceptions.EntidadeEmUsoException;
import com.televisivo.service.exceptions.TemporadaNaoCadastradaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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
	@Transactional(readOnly = true)
    public List<Temporada> findAll() {
        return temporadaRepository.findAll();
    }
    
    @Override
    public Temporada save(Temporada temporada) {
        temporada.setQtdEpisodios(temporada.getEpisodios().size());
        return temporadaRepository.save(temporada);
    }

    @Override
    public Temporada update(Temporada temporada) {
        return save(temporada);
    }

    @Override
	@Transactional(readOnly = true)
    public Temporada getOne(Long id) {
        return temporadaRepository.getOne(id);
    }

    @Override
    public Temporada findById(Long id) {
        return temporadaRepository.findById(id).orElseThrow(() -> new TemporadaNaoCadastradaException(id));
    }

    @Override
    public void deleteById(Long id) {
		try {
			temporadaRepository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("A temporada de código %d não pode ser removida!", id));
		} catch (EmptyResultDataAccessException e){
			throw new TemporadaNaoCadastradaException(String.format("O temporada com o código %d não foi encontrada!", id));
		}
    }

    @Override
    public List<Temporada> buscarNumero(int numero) {
        return temporadaRepository.buscarNumero(numero);
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
        return temporada;
    }

    @Override
    public Temporada buscarPorIdEpisodio(Long id) {
        return temporadaRepository.buscarPorIdEpisodio(id);
    }
}