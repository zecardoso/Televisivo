package com.televisivo.service.impl;

import java.util.List;

import com.televisivo.model.Categoria;
import com.televisivo.repository.CategoriaRepository;
import com.televisivo.repository.filters.CategoriaFilter;
import com.televisivo.service.CategoriaService;
import com.televisivo.service.exceptions.CategoriaNaoCadastradaException;
import com.televisivo.service.exceptions.EntidadeEmUsoException;

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
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    @PreAuthorize("hasPermission('CATEGORIA','LEITURA')")
    @Transactional(readOnly = true)
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    @PreAuthorize("hasPermission('CATEGORIA','INSERIR')")
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    @PreAuthorize("hasPermission('CATEGORIA','ATUALIZAR')")
    public Categoria update(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    @PreAuthorize("hasPermission('CATEGORIA','LEITURA')")
    @Transactional(readOnly = true)
    public Categoria getOne(Long id) {
        return categoriaRepository.getOne(id);
    }

    @Override
    @PreAuthorize("hasPermission('CATEGORIA','LEITURA')")
    public Categoria findById(Long id) {
		return categoriaRepository.findById(id).orElseThrow(()-> new CategoriaNaoCadastradaException(id));
    }

    @Override
    @PreAuthorize("hasPermission('CATEGORIA','EXCLUIR')")
    public void deleteById(Long id) {
		try {
			categoriaRepository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O categoria de código %d não pode ser removido!", id));
		} catch (EmptyResultDataAccessException e){
			throw new CategoriaNaoCadastradaException(String.format("A categoria com o código %d não foi encontrada!", id));
		}
    }

    @Override
    @PreAuthorize("hasPermission('CATEGORIA','LEITURA')")
    public List<Categoria> buscarNome(String nome) {
		return categoriaRepository.buscarNome(nome);
    }

    @Override
    @PreAuthorize("hasPermission('CATEGORIA','LEITURA')")
    public Page<Categoria> listaComPaginacao(CategoriaFilter categoriaFilter, Pageable pageable) {
        return categoriaRepository.listaComPaginacao(categoriaFilter, pageable);
    }
}