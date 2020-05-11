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
    public RolePermissao adicionar(RolePermissao entity) {
        return rolePermissaoRepository.save(entity);
    }

    @Override
    public RolePermissao alterar(RolePermissao entity) {
        return this.adicionar(entity);
    }

    @Override
    public RolePermissao buscarId(RolePermissaoId id) {
        return rolePermissaoRepository.getOne(id);
    }

    @Override
    public void remover(RolePermissaoId id) {
        rolePermissaoRepository.deleteById(id);
    }

    @Override
    public List<RolePermissao> listar() {
        return rolePermissaoRepository.findAll();
    }

    @Override
    public List<RolePermissao> buscarRolePermissaoFilter(RolePermissaoFilter rolePermissaoFilter) {
        return rolePermissaoRepository.buscarRolePermissaoFilter(rolePermissaoFilter);
    }
}