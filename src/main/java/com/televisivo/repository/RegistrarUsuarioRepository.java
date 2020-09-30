package com.televisivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.televisivo.model.Usuario;

@Repository
public interface RegistrarUsuarioRepository extends JpaRepository<Usuario, Long> {

}
