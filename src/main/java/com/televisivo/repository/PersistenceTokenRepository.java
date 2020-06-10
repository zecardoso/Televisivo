package com.televisivo.repository;

import com.televisivo.model.PersistenceTokenLogin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersistenceTokenRepository extends JpaRepository<PersistenceTokenLogin, String> {
    
    @Modifying
    @Query("DELETE FROM PersistenceTokenLogin p WHERE p.username =:username")
    public void deletePersistenceTokenLogin(@Param("username") String username);
}