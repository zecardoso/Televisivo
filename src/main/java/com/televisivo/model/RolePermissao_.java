package com.televisivo.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RolePermissao.class)
public abstract class RolePermissao_ {

	public static volatile SingularAttribute<RolePermissao, Role> role;
	public static volatile SingularAttribute<RolePermissao, Escopo> escopo;
	public static volatile SingularAttribute<RolePermissao, RolePermissaoId> id;
	public static volatile SingularAttribute<RolePermissao, Permissao> permissao;

	public static final String ROLE = "role";
	public static final String ESCOPO = "escopo";
	public static final String ID = "id";
	public static final String PERMISSAO = "permissao";

}

