package com.televisivo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.televisivo.model.Usuario;
import com.televisivo.repository.UsuarioRepository;
import com.televisivo.repository.filters.UsuarioFilter;
import com.televisivo.service.UsuarioService;
import com.televisivo.service.exceptions.ConfirmeSenhaNaoInformadaException;
import com.televisivo.service.exceptions.EmailCadastradoException;
import com.televisivo.service.exceptions.SenhaError;
import com.televisivo.service.exceptions.UsuarioNaoCadastradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasPermission('USUARIO','LEITURA')")
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    @PreAuthorize("hasPermission('USUARIO','INSERIR')")
    public Usuario save(Usuario usuario) {
        Optional<Usuario> usuarioCadastrado = findUsuarioByEmail(usuario.getEmail());
        if (usuarioCadastrado.isPresent() && !usuarioCadastrado.get().equals(usuario)) {
            throw new EmailCadastradoException(String.format("O E-mail %s já está cadastrado no sistema.", usuario.getEmail()));
        }
        if (!usuario.getPassword().equals(usuario.getContraSenha())) {
            throw new ConfirmeSenhaNaoInformadaException("Senha incorreta.");
        }
        if (usuario.getPassword().isBlank() || (usuario.getContraSenha()).isBlank()) {
            throw new SenhaError("A senha não pode estar em branco.");
        }
        usuario.setPassword(encodePassword(usuario.getPassword()));
        usuario.setAtivo(Boolean.TRUE);
        return usuarioRepository.save(usuario);
    }

    @Override
    @PreAuthorize("hasPermission('USUARIO','ATUALIZAR')")
    public Usuario update(Usuario usuario) {
        Usuario usuarioOrg = findById(usuario.getId());
        Optional<Usuario> usuarioCadastrado = findUsuarioByEmail(usuario.getEmail());
        if (usuarioCadastrado.isPresent() && !usuarioCadastrado.get().equals(usuario)) {
            throw new EmailCadastradoException(String.format("O E-mail %s já está cadastrado no sistema.", usuario.getEmail()));
        }
        if (usuario.getPassword().isBlank() && (usuario.getContraSenha()).isBlank()) {
            usuario.setPassword(usuarioOrg.getPassword());
        } else if (!usuario.getPassword().equals(usuario.getContraSenha())) {
            throw new SenhaError("Senha incorreta.");
        } else {
            usuario.setPassword(encodePassword(usuario.getPassword()));
        }
        usuario.setQtdEpisodios(usuarioOrg.getQtdEpisodios());
        usuario.setQtdSeries(usuarioOrg.getQtdSeries());
        usuario.setQtdSeriesArq(usuarioOrg.getQtdSeriesArq());
        usuario.setLastLogin(usuarioOrg.getLastLogin());
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasPermission('USUARIO','LEITURA')")
    public Usuario getOne(Long id) {
        return usuarioRepository.getOne(id);
    }

    @Override
    @PreAuthorize("hasPermission('USUARIO','LEITURA')")
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoCadastradoException(id));
    }

    @Override
    @PreAuthorize("hasPermission('USUARIO','EXCLUIR')")
    public void deleteById(Long id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoCadastradoException(String.format("O usuario com o código %d não foi encontrado!", id));
        }
    }

    @Override
    @PreAuthorize("hasPermission('USUARIO','LEITURA')")
    public List<Usuario> buscarNome(String nome) {
        return usuarioRepository.buscarNome(nome);
    }

    @Override
    @PreAuthorize("hasPermission('USUARIO','LEITURA')")
    public Page<Usuario> listaComPaginacao(UsuarioFilter usuarioFilter, Pageable pageable) {
        return usuarioRepository.listaComPaginacao(usuarioFilter, pageable);
    }

    @Override
    public Optional<Usuario> findUsuarioByEmail(String email) {
        return usuarioRepository.findUsuarioByEmail(email);
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

    @Override
    public void updateFaileAccess(Usuario usuario) {
        Integer totalAcesso = usuario.getFailedLogin() + 1;
        usuario.setFailedLogin(totalAcesso);
        usuarioRepository.save(usuario);
    }

    @Override
    public void blockedUsuario(Usuario usuario) {
        usuario.setAtivo(Boolean.FALSE);
        usuarioRepository.save(usuario);
    }
}