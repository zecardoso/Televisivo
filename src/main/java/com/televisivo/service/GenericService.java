package com.televisivo.service;

import java.util.List;

public interface GenericService<T, I> {

    List<T> findAll();
	T save(T entity);
	T update(T entity);
	T getOne(I id);
	T findById(I id);
    void deleteById(I id);
}