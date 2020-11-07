package com.televisivo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class UsuarioSerieId implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
	@Column(name = "usuario_id", nullable = false, insertable = false, updatable = false)
    private Long usuario;

    @EqualsAndHashCode.Include
	@Column(name = "serie_id", nullable = false, insertable = false, updatable = false)
    private Long serie;
}