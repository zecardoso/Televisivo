package com.televisivo.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Permissao.class)
public abstract class Permissao_ {

	public static volatile SingularAttribute<Permissao, String> nome;
	public static volatile SingularAttribute<Permissao, Long> id;
	public static volatile ListAttribute<Permissao, RolePermissao> rolePermissoes;

	public static final String NOME = "nome";
	public static final String ID = "id";
	public static final String ROLE_PERMISSOES = "rolePermissoes";

}

