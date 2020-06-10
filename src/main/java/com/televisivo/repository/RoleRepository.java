package com.televisivo.repository;

import java.util.List;

import com.televisivo.model.Role;
import com.televisivo.repository.query.RoleQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, RoleQuery {

    @Query("SELECT r FROM Role r WHERE r.nome like %:nome%")
    List<Role> buscarNome(@Param("nome") String nome);

    @Query("SELECT r FROM Role r LEFT JOIN r.usuarios ")
	List<Role> findAllRoles();
}