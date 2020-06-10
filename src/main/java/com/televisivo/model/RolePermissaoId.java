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
public class RolePermissaoId implements Serializable {

    private static final long serialVersionUID = 6291875624901334284L;

    @EqualsAndHashCode.Include
	@Column(name = "role_id", nullable = false, insertable = false, updatable = false)
    private Long role;

    @EqualsAndHashCode.Include
	@Column(name = "permissao_id", nullable = false, insertable = false, updatable = false)
    private Long permissao;

    @EqualsAndHashCode.Include
	@Column(name = "escopo_id", nullable = false, insertable = false, updatable = false)
    private Long escopo;
}
