package com.televisivo.service.impl;

import java.util.List;

import com.televisivo.model.Artista;
import com.televisivo.model.Categoria;
import com.televisivo.model.Elenco;
import com.televisivo.model.Serie;
import com.televisivo.model.Temporada;
import com.televisivo.repository.ArtistaRepository;
import com.televisivo.repository.CategoriaRepository;
import com.televisivo.repository.ElencoRepository;
import com.televisivo.repository.SerieRepository;
import com.televisivo.repository.TemporadaRepository;
import com.televisivo.repository.filters.SerieFilter;
import com.televisivo.service.SerieService;
import com.televisivo.service.exceptions.EntidadeEmUsoException;
import com.televisivo.service.exceptions.SerieNaoCadastradaException;

import org.springframework.beans.factory.annotation.Autowired;
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

    // @Autowired
    // private ElencoRepository elencoRepository;

    // @Autowired
    // private CategoriaRepository categoriaRepository;

    // @Autowired
    // private ArtistaRepository artistaRepository;

    @Override
    public Serie adicionar(Serie serie) {
        return serieRepository.save(serie);
    }

    @Override
    public Serie alterar(Serie serie) {
        return serieRepository.save(serie);
    }

    @Override
    public void remover(Serie serie) {
		try {
			serieRepository.deleteById(serie.getId());
		} catch (EmptyResultDataAccessException e){
			throw new SerieNaoCadastradaException(String.format("A serie com o código %d não foi encontrada!", serie.getId()));
		}
    }

    @Override
	@Transactional(readOnly = true)
    public Serie buscarId(Long id) {
        return serieRepository.findById(id).orElseThrow(() -> new SerieNaoCadastradaException(id));
    }

    @Override
    public List<Serie> buscarNome(String nome) {
        return serieRepository.buscarNome(nome);
    }

    @Override
	@Transactional(readOnly = true)
    public List<Serie> listar() {
        return serieRepository.findAll();
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