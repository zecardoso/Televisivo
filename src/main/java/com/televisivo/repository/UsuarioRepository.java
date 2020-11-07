package com.televisivo.repository;

import java.util.List;

import com.televisivo.model.Serie;
import com.televisivo.model.Usuario;
import com.televisivo.repository.query.UsuarioQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioQuery {

    @Query("SELECT u FROM Usuario u WHERE u.nome like %:nome%")
    List<Usuario> buscarNome(@Param("nome") String nome);

    @Query("SELECT distinct u FROM Usuario u LEFT JOIN FETCH u.roles")
    List<Usuario> findAllUsuarios();
    
    @Query("SELECT s FROM Serie s RIGHT JOIN UsuarioSerie u on u.serie.id = s.id WHERE u.arquivada = :arquivada AND u.usuario.id = :usuarioLogado")
    List<Serie> findAllSeries(@Param("usuarioLogado") Long usuarioLogado, @Param("arquivada") Boolean arquivada);
}