package com.televisivo.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Servico.class)
public abstract class Servico_ {

	public static volatile ListAttribute<Servico, Serie> series;
	public static volatile SingularAttribute<Servico, String> nome;
	public static volatile SingularAttribute<Servico, Long> id;

	public static final String SERIES = "series";
	public static final String NOME = "nome";
	public static final String ID = "id";

}

