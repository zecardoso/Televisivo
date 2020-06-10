package com.televisivo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "persistence")
public class PersistenceTokenLogin implements Serializable {
    
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "persistence_id")
    private String series;

    @Column(name = "persistence_username", nullable = false)
    private String username;

    @Column(name = "persistence_token", nullable = false)
    private String token;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "persistence_last_used", nullable = false, columnDefinition = "DATE")
    private Date lestUsed;
}