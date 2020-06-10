package com.televisivo.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Auditoria.class)
public abstract class Auditoria_ {

	public static volatile SingularAttribute<Auditoria, Usuario> usuario;
	public static volatile SingularAttribute<Auditoria, String> tipoOperacao;
	public static volatile SingularAttribute<Auditoria, Date> dataOperacao;

	public static final String USUARIO = "usuario";
	public static final String TIPO_OPERACAO = "tipoOperacao";
	public static final String DATA_OPERACAO = "dataOperacao";

}

