package com.televisivo.repository;

import java.time.LocalDate;
import java.util.stream.Stream;

import com.televisivo.model.Usuario;
import com.televisivo.model.VerificarToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificarTokenRepository extends JpaRepository<VerificarToken, Long> {
    
    VerificarToken findByToken(String token);
    VerificarToken findByUsuario(Usuario usuario);
    Stream<VerificarToken> findAllByDataExpiradaLessThan(LocalDate now);
    void deleteByDataExpiradaLessThan(LocalDate now);

    @Modifying
    @Query("DELETE FROM VerificarToken vt WHERE vt.dataExpirada <= ?1")
    void deleteByAllDataExpirada(LocalDate now);
}