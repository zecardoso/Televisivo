package com.televisivo.service.impl;

import java.util.List;

import com.televisivo.model.Episodio;
import com.televisivo.model.Serie;
import com.televisivo.model.Temporada;
import com.televisivo.repository.EpisodioRepository;
import com.televisivo.repository.TemporadaRepository;
import com.televisivo.service.TemporadaService;
import com.televisivo.service.exceptions.EntidadeEmUsoException;
import com.televisivo.service.exceptions.EpisodioNaoCadastradoException;
import com.televisivo.service.exceptions.TemporadaNaoCadastradaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasPermission('TEMPORADA','LEITURA')")
    public List<Temporada> findAll() {
        return temporadaRepository.findAll();
    }

    @Override
    @PreAuthorize("hasPermission('TEMPORADA','INSERIR')")
    public Temporada save(Temporada temporada) {
        return temporadaRepository.save(temporada);
    }

    @Override
    @PreAuthorize("hasPermission('TEMPORADA','ATUALIZAR')")
    public Temporada update(Temporada temporada) {
        atualizarQtdEpisodios(temporada);
        return save(temporada);
    }

    @Override
	@Transactional(readOnly = true)
    @PreAuthorize("hasPermission('TEMPORADA','LEITURA')")
    public Temporada getOne(Long id) {
        return temporadaRepository.getOne(id);
    }

    @Override
    @PreAuthorize("hasPermission('TEMPORADA','LEITURA')")
    public Temporada findById(Long id) {
        return temporadaRepository.findById(id).orElseThrow(() -> new TemporadaNaoCadastradaException(id));
    }

    @Override
    @PreAuthorize("hasPermission('TEMPORADA','EXCLUIR')")
    public void deleteById(Long id) {
		try {
			temporadaRepository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("A temporada de código %d não pode ser removida!", id));
		} catch (EmptyResultDataAccessException e){
			throw new TemporadaNaoCadastradaException(String.format("A temporada com o código %d não foi encontrada!", id));
		}
    }

    @Override
    public Temporada findTemporadaByIdEpisodio(Long id) {
        return episodioRepository.getOne(id).getTemporada();
    }

    @Override
    public Serie findSerieByIdTemporada(Long id) {
        return temporadaRepository.getOne(id).getSerie();
    }

    @Override
    public Episodio findEpisodioByIdEpisodio(Long id) {
        return episodioRepository.getOne(id);
    }

    @Override
    @PreAuthorize("hasPermission('TEMPORADA','LEITURA')")
    public List<Episodio> episodios(Long id) {
        return temporadaRepository.episodios(id);
    }

    @Override
    public void atualizarQtdEpisodios(Temporada temporada) {
        temporada.setQtdEpisodios(temporada.getEpisodios().size());
    }

    @Override
    public void atualizarQtdTemporadas(Serie serie) {
        serie.setQtdTemporadas(serie.getTemporadas().size());
    }

    @Override
    @PreAuthorize("hasPermission('TEMPORADA','INSERIR')")
    public void salvarEpisodio(Temporada temporada) {
        for (Episodio episodio : temporada.getEpisodios()) {
            episodio.setTemporada(temporada);
            if (!episodio.getNome().isEmpty() || episodio.getNumero() != 0){
                episodioRepository.save(episodio);
            }
        }
    }

    @Override
    @PreAuthorize("hasPermission('TEMPORADA','INSERIR')")
    public Temporada adicionarEpisodio(Temporada temporada) {
        Episodio episodio = new Episodio();
        episodio.setTemporada(temporada);
        temporada.getEpisodios().add(episodio);
        return temporada;
    }

    @Override
    @PreAuthorize("hasPermission('TEMPORADA','EXCLUIR')")
    public void removerEpisodio(Episodio episodio) {
        try {
			episodioRepository.deleteById(episodio.getId());
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O episodio de código %d não pode ser removido!", episodio.getId()));
		} catch (EmptyResultDataAccessException e){
			throw new EpisodioNaoCadastradoException(String.format("O episodio com o código %d não foi encontrado!", episodio.getId()));
        }
    }

    @Override
    @PreAuthorize("hasPermission('TEMPORADA','INSERIR')")
    public Temporada duplicateRow(Temporada temporada, Episodio episodio) {
        Episodio episodioNew = new Episodio();
        episodioNew.setNome(episodio.getNome());
        episodioNew.setNumero(episodio.getNumero()+1);
        episodioNew.setDuracao(episodio.getDuracao());
        episodioNew.setEnredo(episodio.getEnredo());
        episodio.setTemporada(temporada);
        temporada.getEpisodios().add(episodioNew);
        return temporada;
    }
}