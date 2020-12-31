package com.televisivo.service;

import java.util.Optional;

import com.televisivo.model.Usuario;

public interface ContaService {

    Optional<Usuario> findUsuarioByEmail(String email);
    Usuario update(Usuario usuario);
	Usuario getOne(Long id);
}