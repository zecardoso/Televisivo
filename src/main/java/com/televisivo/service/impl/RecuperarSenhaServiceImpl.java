package com.televisivo.service.impl;

import java.util.Calendar;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.model.Usuario;
import com.televisivo.model.VerificarToken;
import com.televisivo.repository.UsuarioRepository;
import com.televisivo.repository.VerificarTokenRepository;
import com.televisivo.service.RecuperarSenhaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RecuperarSenhaServiceImpl implements RecuperarSenhaService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private VerificarTokenRepository verificarTokenRepository;

	@Override
	public void criarNovaSenhaComTokenParaUsuario(Usuario usuario, String token) {
		VerificarToken tokenUsuario = new VerificarToken(token, usuario);
		verificarTokenRepository.save(tokenUsuario);
	}

	@Override
	@Transactional(readOnly=true)
	public VerificarToken pegarNovaSenhaComToken(String token) {
		return verificarTokenRepository.findByToken(token);
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario pegarUsuarioComNovaSenhaToken(String token) {
		return verificarTokenRepository.findByToken(token).getUsuario();
	}

	@Override
	public void alterarUsuarioSenha(Usuario usuario, String password) {
		usuario.setPassword(encodeUsuarioPassword(password));
		usuario.setContraSenha(encodeUsuarioPassword(password));
		usuarioRepository.save(usuario);
	}
	
	@Override
    public int validarSenhaAlteradaComToken(long id, String token) {
        VerificarToken verificarToken = verificarTokenRepository.findByToken(token);
        if (( verificarToken == null) || (verificarToken.getUsuario().getId() != id)) {
            return TelevisivoConfig.TOKEN_INVALID;
        }
        Calendar cal = Calendar.getInstance();
        if (( verificarToken.getDataExpirada().getTime() - cal.getTime().getTime() <= 0)) {
            return TelevisivoConfig.TOKEN_EXPIRED;
        }
        return TelevisivoConfig.TOKEN_VALID;
    }
	
	private String encodeUsuarioPassword(String password) {
		return passwordEncoder.encode(password);
	}
}