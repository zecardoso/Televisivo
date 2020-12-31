package com.televisivo.repository;

import java.util.List;

import com.televisivo.model.Episodio;
import com.televisivo.model.UsuarioEpisodio;
import com.televisivo.model.UsuarioEpisodioId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioEpisodioRepository extends JpaRepository<UsuarioEpisodio, UsuarioEpisodioId> {

    @Query("SELECT e FROM Episodio e RIGHT JOIN UsuarioEpisodio u on u.episodio.id = e.id WHERE u.usuario.id = :usuarioLogado")
    List<Episodio> findAllEpisodios(@Param("usuarioLogado") Long usuarioLogado);

    @Query("SELECT count(u) FROM UsuarioEpisodio u WHERE u.usuario.id = :usuarioLogado")
    int qtd(@Param("usuarioLogado") Long usuarioLogado);
}