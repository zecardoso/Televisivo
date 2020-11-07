package com.televisivo.service;

import java.util.List;
import java.util.Optional;

import com.televisivo.model.Episodio;
import com.televisivo.model.Serie;
import com.televisivo.model.Usuario;
import com.televisivo.repository.filters.UsuarioFilter;
import com.televisivo.security.UsuarioSistema;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService extends GenericService<Usuario, Long> {

    Page<Usuario> listaComPaginacao(UsuarioFilter usuarioFiltro, Pageable pageable);
    List<Usuario> buscarNome(String nome);
    
    Optional<Usuario> findUsuarioByEmail(String email);
    Optional<Usuario> loginUsuarioByEmail(String email);
	void updateLoginUsuario(Usuario usuario);
	void blockedUsuario(Usuario usuario);
	void updateFaileAccess(Usuario usuario);
    
    List<Episodio> listaEpisodio(UsuarioSistema usuarioLogado);
    List<Serie> findAllSeries(UsuarioSistema usuarioLogado);
    List<Serie> findAllSeriesArq(UsuarioSistema usuarioLogado);
    
    // void salvarCategoria(Usuario usuario);
    // Usuario adicionarCategoria(Usuario usuario);
    // Usuario removerCategoria(Usuario usuario, int index);
    // Usuario buscarPorIdCategoria(Long id);

    // void salvarSerie(Usuario usuario);
    // Usuario adicionarSerie(Usuario usuario);
    // Usuario removerSerie(Usuario usuario, int index);
    // Usuario buscarPorIdSerie(Long id);
}