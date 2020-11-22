package com.televisivo.repository;

import java.util.List;

import com.televisivo.model.Serie;
import com.televisivo.model.UsuarioSerie;
import com.televisivo.model.UsuarioSerieId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioSerieRepository extends JpaRepository<UsuarioSerie, UsuarioSerieId> {

    @Query("SELECT s FROM Serie s RIGHT JOIN UsuarioSerie u on u.serie.id = s.id WHERE u.arquivada = :arquivada AND u.usuario.id = :usuarioLogado")
    List<Serie> findAllSeries(@Param("usuarioLogado") Long usuarioLogado, @Param("arquivada") Boolean arquivada);

    @Query("SELECT count(u) FROM UsuarioSerie u WHERE u.arquivada = :arquivada AND u.usuario.id = :usuarioLogado")
    int qtd(@Param("usuarioLogado") Long usuarioLogado, @Param("arquivada") Boolean arquivada);
}