package com.televisivo.repository;

import com.televisivo.model.UsuarioAuditoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioAuditoriaRepository extends JpaRepository<UsuarioAuditoria, Long> {
    
}