package com.televisivo.service;

import java.util.List;

public interface GenericService<T, ID> {

    T adicionar(T entity);
    T alterar(T entity);
    T buscarId(ID id);
    void remover(ID id);
    List<T> listar();
}
