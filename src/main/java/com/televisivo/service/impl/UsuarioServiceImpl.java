package com.televisivo.service.impl;

import java.util.List;
import java.util.Optional;

import com.televisivo.model.Categoria;
import com.televisivo.model.Usuario;
import com.televisivo.repository.CategoriaRepository;
import com.televisivo.repository.SerieRepository;
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

    // @Autowired
    // private CategoriaRepository categoriaRepository;

    // @Autowired
    // private SerieRepository serieRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Secured("hasRole('ROLE_ADMINISTRADOR')")
    @PreAuthorize("hasPermission('CADASTRO_USUARIO', 'ESCRITA')")
    @Override
    public Usuario adicionar(Usuario usuario) {
        if (!usuario.getPassword().equals(usuario.getContraSenha())) {
            throw new SenhaError("Senha incorreta.");
        }
        Optional<Usuario> optional = buscarEmail(usuario.getEmail());
        if (optional.isPresent()) {
            throw new EmailExistente("Email já cadastrado.");
        }
        usuario.setPassword(criptografarSenha(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    private String criptografarSenha(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public Usuario alterar(Usuario usuario) {
        if (!usuario.getPassword().equals(usuario.getContraSenha())) {
            throw new SenhaError("Senha incorreta.");
        }
        usuario.setPassword(criptografarSenha(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public void remover(Usuario usuario) {
        try {
            usuarioRepository.deleteById(usuario.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoCadastradoException(String.format("O usuario com o código %d não foi encontrado!", usuario.getId()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoCadastradoException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
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
}