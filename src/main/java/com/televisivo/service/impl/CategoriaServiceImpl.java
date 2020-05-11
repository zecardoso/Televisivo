package com.televisivo.service.impl;

import java.util.List;

import com.televisivo.model.Categoria;
import com.televisivo.repository.CategoriaRepository;
import com.televisivo.repository.filters.CategoriaFilter;
import com.televisivo.service.CategoriaService;
import com.televisivo.service.exceptions.CategoriaNaoCadastradaException;
import com.televisivo.service.exceptions.EntidadeEmUsoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Categoria adicionar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria alterar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public void remover(Categoria categoria) {
		try {
			categoriaRepository.deleteById(categoria.getId());
		} catch (EmptyResultDataAccessException e){
			throw new CategoriaNaoCadastradaException(String.format("A categoria com o código %d não foi encontrada!", categoria.getId()));
		}
    }

    @Override
    public Categoria buscarId(Long id) {
		return categoriaRepository.findById(id).orElseThrow(()-> new CategoriaNaoCadastradaException(id));
    }

    @Override
    public List<Categoria> buscarNome(String nome) {
		return categoriaRepository.buscarNome(nome);
    }

    @Override
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    @Override
    public Page<Categoria> listaComPaginacao(CategoriaFilter categoriaFilter, Pageable pageable) {
        return categoriaRepository.listaComPaginacao(categoriaFilter, pageable);
    }
}