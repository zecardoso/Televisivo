package com.televisivo.service.impl;

import java.util.List;

import com.televisivo.model.Episodio;
import com.televisivo.model.Temporada;
import com.televisivo.repository.EpisodioRepository;
import com.televisivo.service.EpisodioService;
import com.televisivo.service.exceptions.EntidadeEmUsoException;
import com.televisivo.service.exceptions.EpisodioNaoCadastradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EpisodioServiceImpl implements EpisodioService {

    @Autowired
    private EpisodioRepository episodioRepository;

    @Override
	@Transactional(readOnly = true)
    @PreAuthorize("hasPermission('EPISODIO','LEITURA')")
    public List<Episodio> findAll() {
        return episodioRepository.findAll();
    }

    @Override
    @PreAuthorize("hasPermission('EPISODIO','INSERIR')")
    public Episodio save(Episodio episodio) {
        return episodioRepository.save(episodio);
    }

    @Override
    @PreAuthorize("hasPermission('EPISODIO','ATUALIZAR')")
    public Episodio update(Episodio episodio) {
        return episodioRepository.save(episodio);
    }

    @Override
	@Transactional(readOnly = true)
    @PreAuthorize("hasPermission('EPISODIO','LEITURA')")
    public Episodio getOne(Long id) {
        return episodioRepository.getOne(id);
    }

    @Override
    @PreAuthorize("hasPermission('EPISODIO','LEITURA')")
    public Episodio findById(Long id) {
        return episodioRepository.findById(id).orElseThrow(() -> new EpisodioNaoCadastradoException(id));
    }

    @Override
    @PreAuthorize("hasPermission('EPISODIO','EXCLUIR')")
    public void deleteById(Long id) {
		try {
			episodioRepository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O episodio de código %d não pode ser removido!", id));
		} catch (EmptyResultDataAccessException e){
			throw new EpisodioNaoCadastradoException(String.format("O episodio com o código %d não foi encontrado!", id));
        }
    }

    @Override
    @PreAuthorize("hasPermission('EPISODIO','LEITURA')")
    public Temporada findTemporadaByIdEpisodio(Long id) {
        return episodioRepository.getOne(id).getTemporada();
    }

    @Override
    public void atualizarQtdEpisodios(Temporada temporada) {
        temporada.setQtdEpisodios(temporada.getEpisodios().size());
    }
}