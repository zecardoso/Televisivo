package com.televisivo.service;

import java.util.List;
import java.util.Optional;

import com.televisivo.model.Usuario;
import com.televisivo.repository.filters.UsuarioFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {

    Usuario adicionar(Usuario usuario);
    Usuario alterar(Usuario usuario);
    void remover(Usuario usuario);
    Usuario buscarId(Long id);
    List<Usuario> buscarNome(String nome);
    List<Usuario> listar();
    Optional<Usuario> buscarEmail(String email);
    Page<Usuario> listaComPaginacao(UsuarioFilter usuarioFiltro, Pageable pageable);
    
    // void salvarCategoria(Usuario usuario);
    // Usuario adicionarCategoria(Usuario usuario);
    // Usuario removerCategoria(Usuario usuario, int index);
    // Usuario buscarPorIdCategoria(Long id);

    // void salvarSerie(Usuario usuario);
    // Usuario adicionarSerie(Usuario usuario);
    // Usuario removerSerie(Usuario usuario, int index);
    // Usuario buscarPorIdSerie(Long id);
}