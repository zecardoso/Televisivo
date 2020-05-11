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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicoServiceImpl implements ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Override
    public Servico adicionar(Servico servico) {
        return servicoRepository.save(servico);
    }

    @Override
    public Servico alterar(Servico servico) {
        return servicoRepository.save(servico);
    }

    @Override
    public void remover(Servico servico) {
		try {
			servicoRepository.deleteById(servico.getId());
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("A Role de código %d não pode ser removida!", servico.getId()));
		} catch (EmptyResultDataAccessException e){
			throw new ServicoNaoCadastradoException(String.format("A serviço com o código %d não foi encontrada!", servico.getId()));
		}
    }

    @Override
	@Transactional(readOnly = true)
    public Servico buscarId(Long id) {
        return servicoRepository.findById(id).orElseThrow(() -> new ServicoNaoCadastradoException(id));
    }

    @Override
    public List<Servico> buscarNome(String nome) {
        return servicoRepository.buscarNome(nome);
    }

    @Override
	@Transactional(readOnly = true)
    public List<Servico> listar() {
        return servicoRepository.findAll();
    }

    @Override
    public Page<Servico> listaComPaginacao(ServicoFilter servicoFilter, Pageable pageable) {
        return servicoRepository.listaComPaginacao(servicoFilter, pageable);
    }
}