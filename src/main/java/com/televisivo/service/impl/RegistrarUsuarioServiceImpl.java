package com.televisivo.service.impl;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.model.Usuario;
import com.televisivo.model.VerificarToken;
import com.televisivo.repository.UsuarioRepository;
import com.televisivo.repository.VerificarTokenRepository;
import com.televisivo.service.RegistrarUsuarioService;
import com.televisivo.service.exceptions.ConfirmeSenhaNaoInformadaException;
import com.televisivo.service.exceptions.EmailCadastradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrarUsuarioServiceImpl implements RegistrarUsuarioService {
    
	@Autowired
    private VerificarTokenRepository verificarTokenRepository;
    
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Usuario registrarUsuario(Usuario usuario) {
		Optional<Usuario> usuarioCadastrado = this.findUsuarioByEmail(usuario.getEmail());
		if (usuarioCadastrado.isPresent() && !usuarioCadastrado.get().equals(usuario)) {
			throw new EmailCadastradoException(String.format("O E-mail %s já está cadastrado no sistema ", usuario.getEmail()));
		}
		if (usuario.getContraSenha().equals("")) {
	    	throw new ConfirmeSenhaNaoInformadaException("A senha não pode estar em branco!");
		}
		usuario.setAtivo(false);
		usuario.setPassword(encodeUsuarioPassword(usuario.getPassword()));
		return usuarioRepository.save(usuario);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Usuario getUsuario(String verificationToken) {
	    VerificarToken token = verificarTokenRepository.findByToken(verificationToken);
		if (token != null) {
			return token.getUsuario();
		}
		return null;
	}
	
	@Override
	public int verificarValidacaoToken(String token) {
		VerificarToken verificarToken = verificarTokenRepository.findByToken(token);
        if (verificarToken == null) {
            return TelevisivoConfig.TOKEN_INVALID;
        }
        Usuario usuario = verificarToken.getUsuario();
        Calendar cal = Calendar.getInstance();
        if ((verificarToken.getDataExpirada().getTime() - cal.getTime().getTime()) <= 0) {
            return TelevisivoConfig.TOKEN_EXPIRED;
        }
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
        return TelevisivoConfig.TOKEN_VALID;
	}

	@Override
	public void criaTokenVerificacaoUsuario(Usuario usuario, String token) {
		VerificarToken myToken = new VerificarToken(token, usuario);
        verificarTokenRepository.save(myToken);
	}

	@Override
	public VerificarToken renovarValidacaoToken(String token) {
		VerificarToken myToken = verificarTokenRepository.findByToken(token);
		myToken.updateToken(UUID.randomUUID().toString());
		myToken = verificarTokenRepository.save(myToken);
		return myToken;
	}

    @Override
	public Optional<Usuario> findUsuarioByEmail(String email){
		return usuarioRepository.findUsuarioByEmail(email);
	}
	
	private String encodeUsuarioPassword(String password) {
		 return passwordEncoder.encode(password);
	}
}