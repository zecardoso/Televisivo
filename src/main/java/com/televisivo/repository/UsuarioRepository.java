package com.televisivo.repository;

import java.util.List;

import com.televisivo.model.Usuario;
import com.televisivo.repository.query.UsuarioQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioQuery {

    @Query(value = "SELECT u FROM Usuario u WHERE u.nome like %:nome%")
    List<Usuario> buscarNome(@Param("nome") String nome);
}