package com.televisivo.service;

import java.util.List;

import com.televisivo.model.Servico;
import com.televisivo.repository.filters.ServicoFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServicoService {

    Servico adicionar(Servico servico);
    Servico alterar(Servico servico);
    void remover(Servico servico);
    Servico buscarId(Long id);
    List<Servico> buscarNome(String nome);
    List<Servico> listar();
    Page<Servico> listaComPaginacao(ServicoFilter servicoFilter, Pageable pageable);
}