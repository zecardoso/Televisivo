package com.televisivo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "role_permissao")
public class RolePermissao implements Serializable {

    @EmbeddedId
    private RolePermissaoId id;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "permissao_id", insertable = false, updatable = false)
    private Permissao permissao;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "escopo_id", insertable = false, updatable = false)
    private Escopo escopo;
}