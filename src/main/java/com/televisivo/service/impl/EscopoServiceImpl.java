package com.televisivo.service.impl;

import java.util.List;

import com.televisivo.model.Escopo;
import com.televisivo.repository.EscopoRepository;
import com.televisivo.repository.filters.EscopoFilter;
import com.televisivo.service.EscopoService;
import com.televisivo.service.exceptions.EntidadeEmUsoException;
import com.televisivo.service.exceptions.EscopoNaoCadastradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EscopoServiceImpl implements EscopoService {

    @Autowired
    private EscopoRepository escopoRepository;

    @Override
	@Transactional(readOnly = true)
    @PreAuthorize("hasPermission('ESCOPO','LEITURA')")
    public List<Escopo> findAll() {
        return escopoRepository.findAll();
    }

    @Override
    @PreAuthorize("hasPermission('ESCOPO','INSERIR')")
    public Escopo save(Escopo escopo) {
        return escopoRepository.save(escopo);
    }

    @Override
    @PreAuthorize("hasPermission('ESCOPO','ATUALIZAR')")
    public Escopo update(Escopo escopo) {
        return escopoRepository.save(escopo);
    }

    @Override
	@Transactional(readOnly = true)
    @PreAuthorize("hasPermission('ESCOPO','LEITURA')")
    public Escopo getOne(Long id) {
        return escopoRepository.getOne(id);
    }

    @Override
    @PreAuthorize("hasPermission('ESCOPO','LEITURA')")
    public Escopo findById(Long id) {
		return escopoRepository.findById(id).orElseThrow(()-> new EscopoNaoCadastradoException(id));
    }

    @Override
    @PreAuthorize("hasPermission('ESCOPO','EXCLUIR')")
    public void deleteById(Long id) {
        try {
			escopoRepository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O escopo de código %d não pode ser removido!", id));
		} catch (EmptyResultDataAccessException e){
			throw new EscopoNaoCadastradoException(String.format("O escopo com o código %d não foi encontrado!", id));
		}
    }

    @Override
    @PreAuthorize("hasPermission('ESCOPO','LEITURA')")
    public List<Escopo> buscarNome(String nome) {
        return escopoRepository.buscarNome(nome);
    }

    @Override
    @PreAuthorize("hasPermission('ESCOPO','LEITURA')")
    public Page<Escopo> listaComPaginacao(EscopoFilter escopoFilter, Pageable pageable) {
        return escopoRepository.listaComPaginacao(escopoFilter, pageable);
    }
}