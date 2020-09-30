package com.televisivo.service;

import com.televisivo.model.Usuario;
import com.televisivo.model.VerificarToken;

public interface RecuperarSenhaService {
	
	void criarNovaSenhaComTokenParaUsuario(Usuario usuario, String token);
	VerificarToken pegarNovaSenhaComToken(String token);
	Usuario pegarUsuarioComNovaSenhaToken(String token);
	void alterarUsuarioSenha(Usuario usuario, String password);
	int validarSenhaAlteradaComToken(long id, String token);
}