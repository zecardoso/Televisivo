package com.televisivo.service.impl;

import java.util.List;

import com.televisivo.model.Episodio;
import com.televisivo.repository.EpisodioRepository;
import com.televisivo.service.EpisodioService;
import com.televisivo.service.exceptions.EntidadeEmUsoException;
import com.televisivo.service.exceptions.EpisodioNaoCadastradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EpisodioServiceImpl implements EpisodioService {

    @Autowired
    private EpisodioRepository episodioRepository;

    @Override
	@Transactional(readOnly = true)
    public List<Episodio> findAll() {
        return episodioRepository.findAll();
    }

    @Override
    public Episodio save(Episodio episodio) {
        return episodioRepository.save(episodio);
    }

    @Override
    public Episodio update(Episodio episodio) {
        return episodioRepository.save(episodio);
    }

    @Override
	@Transactional(readOnly = true)
    public Episodio getOne(Long id) {
        return episodioRepository.getOne(id);
    }

    @Override
    public Episodio findById(Long id) {
        return episodioRepository.findById(id).orElseThrow(() -> new EpisodioNaoCadastradoException(id));
    }

    @Override
    public void deleteById(Long id) {
		try {
			episodioRepository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O artista de código %d não pode ser removido!", id));
		} catch (EmptyResultDataAccessException e){
			throw new EpisodioNaoCadastradoException(String.format("O episodio com o código %d não foi encontrado!", id));
		}
    }
    
    @Override
    public List<Episodio> buscarNumero(int numero) {
        return episodioRepository.buscarNumero(numero);
    }
}