package com.televisivo.service.impl;

import java.util.List;

import com.televisivo.model.Elenco;
import com.televisivo.repository.ElencoRepository;
import com.televisivo.repository.filters.ElencoFilter;
import com.televisivo.service.ElencoService;
import com.televisivo.service.exceptions.ElencoNaoCadastradoException;
import com.televisivo.service.exceptions.EntidadeEmUsoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ElencoServiceImpl implements ElencoService {

    @Autowired
    private ElencoRepository elencoRepository;

    @Override
    public Elenco adicionar(Elenco elenco) {
        return elencoRepository.save(elenco);
    }

    @Override
    public Elenco alterar(Elenco elenco) {
        return elencoRepository.save(elenco);
    }

    @Override
    public void remover(Elenco elenco) {
		try {
			elencoRepository.deleteById(elenco.getId());
		} catch (EmptyResultDataAccessException e){
			throw new ElencoNaoCadastradoException(String.format("O elenco com o código %d não foi encontrado!", elenco.getId()));
		}
    }

    @Override
	@Transactional(readOnly = true)
    public Elenco buscarId(Long id) {
		return elencoRepository.findById(id).orElseThrow(()-> new ElencoNaoCadastradoException(id));
    }

	@Override
	public List<Elenco> buscarPersonagem(String personagem) {
		return elencoRepository.buscarPersonagem(personagem);
	}

    @Override
	@Transactional(readOnly = true)
    public List<Elenco> listar() {
        return elencoRepository.findAll();
    }

    @Override
    public Page<Elenco> listaComPaginacao(ElencoFilter elencoFilter, Pageable pageable) {
        return elencoRepository.listaComPaginacao(elencoFilter, pageable);
    }
}