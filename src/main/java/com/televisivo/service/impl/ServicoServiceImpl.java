package com.televisivo.service.impl;

import java.util.List;

import com.televisivo.model.Servico;
import com.televisivo.repository.ServicoRepository;
import com.televisivo.repository.filters.ServicoFilter;
import com.televisivo.service.ServicoService;
import com.televisivo.service.exceptions.EntidadeEmUsoException;
import com.televisivo.service.exceptions.ServicoNaoCadastradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Secured("hasRole('ADMINISTRADOR')")
public class ServicoServiceImpl implements ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Override
	@Transactional(readOnly = true)
    @PreAuthorize("hasPermission('SERVICO','LEITURA')")
    public List<Servico> findAll() {
        return servicoRepository.findAll();
    }

    @Override
    @PreAuthorize("hasPermission('SERVICO','INSERIR')")
    public Servico save(Servico servico) {
        return servicoRepository.save(servico);
    }

    @Override
    @PreAuthorize("hasPermission('SERVICO','ATUALIZAR')")
    public Servico update(Servico servico) {
        return servicoRepository.save(servico);
    }

    @Override
	@Transactional(readOnly = true)
    @PreAuthorize("hasPermission('SERVICO','LEITURA')")
    public Servico getOne(Long id) {
        return servicoRepository.getOne(id);
    }

    @Override
    @PreAuthorize("hasPermission('SERVICO','LEITURA')")
    public Servico findById(Long id) {
        return servicoRepository.findById(id).orElseThrow(() -> new ServicoNaoCadastradoException(id));
    }

    @Override
    @PreAuthorize("hasPermission('SERVICO','EXCLUIR')")
    public void deleteById(Long id) {
		try {
			servicoRepository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O serviço de código %d não pode ser removida!", id));
		} catch (EmptyResultDataAccessException e){
			throw new ServicoNaoCadastradoException(String.format("O serviço com o código %d não foi encontrada!", id));
		}
    }

    @Override
    @PreAuthorize("hasPermission('SERVICO','LEITURA')")
    public List<Servico> buscarNome(String nome) {
        return servicoRepository.buscarNome(nome);
    }

    @Override
    @PreAuthorize("hasPermission('SERVICO','LEITURA')")
    public Page<Servico> listaComPaginacao(ServicoFilter servicoFilter, Pageable pageable) {
        return servicoRepository.listaComPaginacao(servicoFilter, pageable);
    }
}