package com.televisivo.service.impl;

import java.util.List;

import com.televisivo.model.Role;
import com.televisivo.repository.RoleRepository;
import com.televisivo.repository.filters.RoleFilter;
import com.televisivo.service.RoleService;
import com.televisivo.service.exceptions.EntidadeEmUsoException;
import com.televisivo.service.exceptions.RoleNaoCadastradaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    
    @Override
	@Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role update(Role role) {
        return roleRepository.save(role);
    }

    @Override
	@Transactional(readOnly = true)
    public Role getOne(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNaoCadastradaException(id));
    }

    @Override
    public void deleteById(Long id) {
        try {
            roleRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("A Role de código %d não pode ser removida.", id));
        } catch (EmptyResultDataAccessException e) {
            throw new RoleNaoCadastradaException(String.format("A role com o código %d não foi encontrada.", id));
        }
    }

    @Override
    public List<Role> buscarNome(String nome) {
        return roleRepository.buscarNome(nome);
    }

    @Override
    public Page<Role> listaComPaginacao(RoleFilter roleFilter, Pageable pageable) {
        return roleRepository.listaComPaginacao(roleFilter, pageable);
    }
}