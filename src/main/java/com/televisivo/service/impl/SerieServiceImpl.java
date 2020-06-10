package com.televisivo.service.impl;

import java.util.List;

import com.televisivo.model.Serie;
import com.televisivo.model.Temporada;
import com.televisivo.repository.SerieRepository;
import com.televisivo.repository.TemporadaRepository;
import com.televisivo.repository.filters.SerieFilter;
import com.televisivo.service.SerieService;
import com.televisivo.service.exceptions.EntidadeEmUsoException;
import com.televisivo.service.exceptions.SerieNaoCadastradaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SerieServiceImpl implements SerieService {

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private TemporadaRepository temporadaRepository;
    
    @Override
	@Transactional(readOnly = true)
    public List<Serie> findAll() {
        return serieRepository.findAll();
    }

    @Override
    public Serie save(Serie serie) {
        serie.setQtdTemporadas(serie.getTemporadas().size());
        return serieRepository.save(serie);
    }

    @Override
    public Serie update(Serie serie) {
        return save(serie);
    }

    @Override
	@Transactional(readOnly = true)
    public Serie getOne(Long id) {
        return serieRepository.getOne(id);
    }

    @Override
    public Serie findById(Long id) {
        return serieRepository.findById(id).orElseThrow(() -> new SerieNaoCadastradaException(id));
    }

    @Override
    public void deleteById(Long id) {
		try {
			serieRepository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("A serie de código %d não pode ser removida!", id));
		} catch (EmptyResultDataAccessException e){
			throw new SerieNaoCadastradaException(String.format("A serie com o código %d não foi encontrada!", id));
		}
    }

    @Override
    public List<Serie> buscarNome(String nome) {
        return serieRepository.buscarNome(nome);
    }

    @Override
    public Page<Serie> listaComPaginacao(SerieFilter serieFilter, Pageable pageable) {
        return serieRepository.listaComPaginacao(serieFilter, pageable);
    }
    
    @Override
    public void salvarTemporada(Serie serie) {
        if (serie.getTemporadas().size() != -1) {
            for (Temporada temporada: serie.getTemporadas()) {
                temporada.setSerie(serie);
                temporadaRepository.save(temporada);
            }
        }
    }

    @Override
    public Serie adicionarTemporada(Serie serie) {
        Temporada temporada = new Temporada();
        temporada.setSerie(serie);
        serie.getTemporadas().add(temporada);
        return serie;
    }

    @Override
    public Serie removerTemporada(Serie serie, int index) {
        Temporada temporada = serie.getTemporadas().get(index);
        if (temporada.getId() != null) {
            temporadaRepository.deleteById(temporada.getId());
        }
        serie.getTemporadas().remove(index);
        return serie;
    }

    @Override
    public Serie buscarPorIdTemporada(Long id) {
        return serieRepository.buscarPorIdTemporada(id);
    }
}