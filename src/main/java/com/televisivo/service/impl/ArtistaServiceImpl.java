package com.televisivo.service.impl;

import java.util.List;

import com.televisivo.model.Artista;
import com.televisivo.repository.ArtistaRepository;
import com.televisivo.repository.filters.ArtistaFilter;
import com.televisivo.service.ArtistaService;
import com.televisivo.service.exceptions.ArtistaNaoCadastradoException;
import com.televisivo.service.exceptions.EntidadeEmUsoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArtistaServiceImpl implements ArtistaService {

    @Autowired
    private ArtistaRepository artistaRepository;

    // @Autowired
    // private CategoriaRepository categoriaRepository;

    @Override
    public Artista adicionar(Artista artista) {
        return artistaRepository.save(artista);
    }

    @Override
    public Artista alterar(Artista artista) {
        return artistaRepository.save(artista);
    }

    @Override
    public void remover(Artista artista) {
		try {
			artistaRepository.deleteById(artista.getId());
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O artista de código %d não pode ser removido!", artista.getId()));
		} catch (EmptyResultDataAccessException e){
			throw new ArtistaNaoCadastradoException(String.format("O artista com o código %d não foi encontrado!", artista.getId()));
		}
    }

    @Override
    public Artista buscarId(Long id) {
		return artistaRepository.findById(id).orElseThrow(()-> new ArtistaNaoCadastradoException(id));
    }

    @Override
    public List<Artista> buscarNome(String nome) {
        return artistaRepository.buscarNome(nome);
    }

    @Override
    public List<Artista> listar() {
        return artistaRepository.findAll();
    }

    @Override
    public Page<Artista> listaComPaginacao(ArtistaFilter artistaFilter, Pageable pageable) {
        return artistaRepository.listaComPaginacao(artistaFilter, pageable);
    }
}