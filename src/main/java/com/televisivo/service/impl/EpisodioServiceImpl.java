package com.televisivo.service.impl;

import java.util.List;

import com.televisivo.model.Episodio;
import com.televisivo.repository.ElencoRepository;
import com.televisivo.repository.EpisodioRepository;
import com.televisivo.repository.filters.EpisodioFilter;
import com.televisivo.service.EpisodioService;
import com.televisivo.service.exceptions.EntidadeEmUsoException;
import com.televisivo.service.exceptions.EpisodioNaoCadastradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EpisodioServiceImpl implements EpisodioService {

    @Autowired
    private EpisodioRepository episodioRepository;

    // @Autowired
    // private ElencoRepository elencoRepository;

    @Override
    public Episodio adicionar(Episodio episodio) {
        return episodioRepository.save(episodio);
    }

    @Override
    public Episodio alterar(Episodio episodio) {
        return episodioRepository.save(episodio);
    }

    @Override
    public void remover(Episodio episodio) {
		try {
			episodioRepository.deleteById(episodio.getId());
		} catch (EmptyResultDataAccessException e){
			throw new EpisodioNaoCadastradoException(String.format("O episodio com o código %d não foi encontrado!", episodio.getId()));
		}
    }

    @Override
    public Episodio buscarId(Long id) {
        return episodioRepository.findById(id).orElseThrow(() -> new EpisodioNaoCadastradoException(id));
    }

    @Override
    public List<Episodio> buscarNumero(int numero) {
        return episodioRepository.buscarNumero(numero);
    }

    @Override
    public List<Episodio> listar() {
        return episodioRepository.findAll();
    }

    @Override
    public Page<Episodio> listaComPaginacao(EpisodioFilter episodioFilter, Pageable pageable) {
        return episodioRepository.listaComPaginacao(episodioFilter, pageable);
    }
}