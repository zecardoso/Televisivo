package com.televisivo.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "usuario_serie")
public class UsuarioSerie implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private UsuarioSerieId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serie_id", insertable = false, updatable = false)
    private Serie serie;

    private Boolean arquivada;
}