package com.televisivo.service.impl;

import java.util.Date;
import java.util.List;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.model.Role;
import com.televisivo.model.UsuarioAuditoria;
import com.televisivo.repository.RoleRepository;
import com.televisivo.repository.UsuarioAuditoriaRepository;
import com.televisivo.repository.filters.RoleFilter;
import com.televisivo.service.RoleService;
import com.televisivo.service.exceptions.EntidadeEmUsoException;
import com.televisivo.service.exceptions.RoleNaoCadastradaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Secured("hasRole('ADMINISTRADOR')")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsuarioAuditoriaRepository usuarioAuditoriaRepository;

    @Override
	@Transactional(readOnly = true)
    @PreAuthorize("hasPermission('ROLE','LEITURA')")
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    @PreAuthorize("hasPermission('ROLE','INSERIR')")
    public Role save(Role role) {
        role = roleRepository.save(role);
        // saveUsuarioAuditoria(role, TelevisivoConfig.INCLUSAO);
        return role;
    }

    @Override
    @PreAuthorize("hasPermission('ROLE','ATUALIZAR')")
    public Role update(Role role) {
        // saveUsuarioAuditoria(role, TelevisivoConfig.ALTERACAO);
        return save(role);
    }

    @Override
	@Transactional(readOnly = true)
    @PreAuthorize("hasPermission('ROLE','LEITURA')")
    public Role getOne(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    @PreAuthorize("hasPermission('ROLE','LEITURA')")
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNaoCadastradaException(id));
    }

    @Override
    @PreAuthorize("hasPermission('ROLE','EXCLUIR')")
    public void deleteById(Long id) {
        try {
            // saveUsuarioAuditoria(getOne(id), TelevisivoConfig.EXCLUSAO);
            roleRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("A Role de código %d não pode ser removida.", id));
        } catch (EmptyResultDataAccessException e) {
            throw new RoleNaoCadastradaException(String.format("A role com o código %d não foi encontrada.", id));
        }
    }

    @Override
    @PreAuthorize("hasPermission('ROLE','LEITURA')")
    public List<Role> buscarNome(String nome) {
        return roleRepository.buscarNome(nome);
    }

    @Override
    @PreAuthorize("hasPermission('ROLE','LEITURA')")
    public Page<Role> listaComPaginacao(RoleFilter roleFilter, Pageable pageable) {
        return roleRepository.listaComPaginacao(roleFilter, pageable);
    }

    @Override
	@Transactional
	public void saveUsuarioAuditoria(Role role, String operacao) {
		UsuarioAuditoria usuarioAuditoria = new UsuarioAuditoria();
		usuarioAuditoria.getAuditoria().setDataOperacao(new Date());
		usuarioAuditoria.getAuditoria().setUsuario(TelevisivoConfig.pegarUsuario());
		usuarioAuditoria.getAuditoria().setTipoOperacao(operacao);
		usuarioAuditoria.setRole(role);
        usuarioAuditoriaRepository.save(usuarioAuditoria);
	}
}