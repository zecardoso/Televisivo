package com.televisivo.service;

import java.util.Optional;

import com.televisivo.model.Usuario;
import com.televisivo.model.VerificarToken;

public interface RegistrarUsuarioService {
    
    Usuario registrarUsuario(Usuario usuario);
    Usuario getUsuario(String verificationToken);
    int verificarValidacaoToken(String token);
    void criaTokenVerificacaoUsuario(Usuario usuario, String token);
    VerificarToken renovarValidacaoToken(String token);
    Optional<Usuario> findUsuarioByEmail(String email);
}
