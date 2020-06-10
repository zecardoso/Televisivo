package com.televisivo.service.impl;

import java.util.List;

import com.televisivo.model.RolePermissao;
import com.televisivo.model.RolePermissaoId;
import com.televisivo.repository.RolePermissaoRepository;
import com.televisivo.repository.filters.RolePermissaoFilter;
import com.televisivo.service.RolePermissaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RolePermissaoServiceImpl implements RolePermissaoService {

    @Autowired
    private RolePermissaoRepository rolePermissaoRepository;
    
    @Override
	@Transactional(readOnly = true)
    public List<RolePermissao> findAll() {
        return rolePermissaoRepository.findAll();
    }

    @Override
    public RolePermissao save(RolePermissao rolePermissao) {
        rolePermissaoRepository.flush();
        return rolePermissaoRepository.save(rolePermissao);
    }

    @Override
    public RolePermissao update(RolePermissao rolePermissao) {
        return rolePermissaoRepository.save(rolePermissao);
    }

    @Override
	@Transactional(readOnly = true)
    public RolePermissao getOne(RolePermissaoId id) {
        return rolePermissaoRepository.getOne(id);
    }

    @Override
    public RolePermissao findById(RolePermissaoId id) {
        return rolePermissaoRepository.getOne(id);
    }

    @Override
    public void deleteById(RolePermissaoId id) {
        rolePermissaoRepository.deleteById(id);
    }

    @Override
    public List<RolePermissao> findRolePermissaoEscopoFilter(RolePermissaoFilter rolePermissaoFilter) {
        return rolePermissaoRepository.findRolePermissaoEscopoFilter(rolePermissaoFilter);
    }
}