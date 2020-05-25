package com.televisivo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.televisivo.model.Usuario;
import com.televisivo.repository.UsuarioRepository;
import com.televisivo.repository.filters.UsuarioFilter;
import com.televisivo.service.UsuarioService;
import com.televisivo.service.exceptions.EmailExistente;
import com.televisivo.service.exceptions.SenhaError;
import com.televisivo.service.exceptions.UsuarioNaoCadastradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String criptografarSenha(String password) {
        return passwordEncoder.encode(password);
    }
    
    @Override
	@Transactional(readOnly = true)
    @Secured("hasRole('ADMINISTRADOR')")
	@PreAuthorize("hasPermission('USUARIO','LEITURA')")
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
	@Secured("hasRole('ADMINISTRADOR')")
	@PreAuthorize("hasPermission('USUARIO','INSERIR')")
    public Usuario save(Usuario usuario) {
        Optional<Usuario> usuarioCadastrado  = buscarEmail(usuario.getEmail());
        if (usuarioCadastrado .isPresent() && !usuarioCadastrado.get().equals(usuario)) {
            throw new EmailExistente(String.format("O E-mail %s já está cadastrado no sistema ", usuario.getEmail()));
        }
        if (!usuario.getPassword().equals(usuario.getContraSenha())) {
            throw new SenhaError("Senha incorreta.");
        }
        usuario.setPassword(criptografarSenha(usuario.getPassword()));
        usuario.setAtivo(Boolean.TRUE);
        return usuarioRepository.save(usuario);
    }

    @Override
	@Secured("hasRole('ADMINISTRADOR')")
	@PreAuthorize("hasPermission('USUARIO','ATUALIZAR')")
    public Usuario update(Usuario usuario) {
        return this.save(usuario);
    }

    @Override
	@Transactional(readOnly = true)
	@Secured("hasRole('ADMINISTRADOR')")
	@PreAuthorize("hasPermission('USUARIO','LEITURA')")
    public Usuario getOne(Long id) {
		return usuarioRepository.getOne(id);
    }

    @Override
	@Secured("hasRole('ADMINISTRADOR')")
	@PreAuthorize("hasPermission('USUARIO','LEITURA')")
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoCadastradoException(id));
    }

    @Override
	@Secured("hasRole('ADMINISTRADOR')")
	@PreAuthorize("hasPermission('USUARIO','EXCLUIR')")
    public void deleteById(Long id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoCadastradoException(String.format("O usuario com o código %d não foi encontrado!", id));
        }
    }

    @Override
    public List<Usuario> buscarNome(String nome) {
        return usuarioRepository.buscarNome(nome);
    }

    @Override
    public Optional<Usuario> buscarEmail(String email) {
        return usuarioRepository.buscarAtivoPorEmail(email);
    }

    @Override
    public Page<Usuario> listaComPaginacao(UsuarioFilter usuarioFilter, Pageable pageable) {
        return usuarioRepository.listaComPaginacao(usuarioFilter, pageable);
    }

    @Override
    public Optional<Usuario> loginUsuarioByEmail(String email) {
        return usuarioRepository.loginUsuarioByEmail(email);
    }

    @Override
    public void updateLoginUsuario(Usuario usuario) {
        usuario.setLastLogin(new Date());
        usuario.setFailedLogin(0);
        usuarioRepository.save(usuario);
    }
}