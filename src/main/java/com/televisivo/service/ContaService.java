package com.televisivo.service;

import java.util.Optional;

import com.televisivo.model.Usuario;

public interface ContaService {

    Optional<Usuario> findUsuarioByEmail(String email);
    Optional<Usuario> findUsuarioByUsername(String username);
    Usuario save(Usuario usuario);
    Usuario update(Usuario usuario);
    Usuario updateSenha(Usuario usuario);
    Usuario getOne(Usuario usuario);
}