package com.televisivo.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UsuarioAuditoria.class)
public abstract class UsuarioAuditoria_ {

	public static volatile SingularAttribute<UsuarioAuditoria, Role> role;
	public static volatile SingularAttribute<UsuarioAuditoria, Long> id;
	public static volatile SingularAttribute<UsuarioAuditoria, Auditoria> auditoria;

	public static final String ROLE = "role";
	public static final String ID = "id";
	public static final String AUDITORIA = "auditoria";

}

