package com.televisivo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.televisivo.model.Role;
import com.televisivo.model.Usuario;
import com.televisivo.repository.RoleRepository;
import com.televisivo.repository.UsuarioRepository;
import com.televisivo.service.ContaService;
import com.televisivo.service.exceptions.EmailCadastradoException;
import com.televisivo.service.exceptions.SenhaError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContaServiceImpl implements ContaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public Usuario save(Usuario usuario) {
        Optional<Usuario> usuarioCadastrado = findUsuarioByEmail(usuario.getEmail());
        if (usuarioCadastrado.isPresent() && !usuarioCadastrado.get().equals(usuario)) {
            throw new EmailCadastradoException(String.format("O E-mail %s já está cadastrado no sistema.", usuario.getEmail()));
        }
        if (!usuario.getPassword().equals(usuario.getContraSenha())) {
            throw new SenhaError("Senha incorreta.");
        }
        if (usuario.getPassword().isBlank() || (usuario.getContraSenha()).isBlank()) {
            throw new SenhaError("A senha não pode estar em branco.");
        }
        usuario.setPassword(encodePassword(usuario.getPassword()));
        usuario.setAtivo(Boolean.TRUE);
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.getOne((long) 2));
        usuario.setRoles(roles);
        return usuarioRepository.save(usuario);
    }

    @Override
    @PreAuthorize("hasPermission('USUARIO','ATUALIZAR')")
    public Usuario update(Usuario usuario) {
        Usuario usuarioOrg = getOne(usuario);
        Optional<Usuario> usuarioCadastrado = findUsuarioByEmail(usuario.getEmail());
        if (usuarioCadastrado.isPresent() && !usuarioCadastrado.get().equals(usuario)) {
            throw new EmailCadastradoException(String.format("O E-mail %s já está cadastrado no sistema.", usuario.getEmail()));
        }
        usuario.setPassword(encodePassword(usuarioOrg.getPassword()));
        usuario.setQtdEpisodios(usuarioOrg.getQtdEpisodios());
        usuario.setQtdSeries(usuarioOrg.getQtdSeries());
        usuario.setQtdSeriesArq(usuarioOrg.getQtdSeriesArq());
        usuario.setLastLogin(usuarioOrg.getLastLogin());
        usuario.setAtivo(usuarioOrg.isAtivo());
        usuario.setRoles(usuarioOrg.getRoles());
        return usuarioRepository.save(usuario);
    }

    @Override
    @PreAuthorize("hasPermission('USUARIO','ATUALIZAR')")
    public Usuario updateSenha(Usuario usuario) {
        Usuario usuarioOrg = getOne(usuario);
        if (usuario.getPassword().isBlank() && (usuario.getContraSenha()).isBlank()) {
            throw new SenhaError("Digite a senha");
        } else if (!usuario.getPassword().equals(usuario.getContraSenha())) {
            throw new SenhaError("Senha incorreta.");
        } else {
            usuario.setPassword(encodePassword(usuario.getPassword()));
        }
        usuario.setNome(usuarioOrg.getNome());
        usuario.setSobrenome(usuarioOrg.getSobrenome());
        usuario.setUsername(usuarioOrg.getUsername());
        usuario.setEmail(usuarioOrg.getEmail());
        usuario.setGenero(usuarioOrg.getGenero());
        usuario.setQtdEpisodios(usuarioOrg.getQtdEpisodios());
        usuario.setQtdSeries(usuarioOrg.getQtdSeries());
        usuario.setQtdSeriesArq(usuarioOrg.getQtdSeriesArq());
        usuario.setLastLogin(usuarioOrg.getLastLogin());
        usuario.setAtivo(usuarioOrg.isAtivo());
        usuario.setRoles(usuarioOrg.getRoles());
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasPermission('USUARIO','LEITURA')")
    public Usuario getOne(Usuario usuario) {
        return usuarioRepository.getOne(usuario.getId());
    }

    @Override
    public Optional<Usuario> findUsuarioByEmail(String email) {
        return usuarioRepository.findUsuarioByEmail(email);
    }
}