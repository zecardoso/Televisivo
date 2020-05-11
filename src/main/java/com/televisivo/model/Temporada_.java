package com.televisivo.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Temporada.class)
public abstract class Temporada_ {

	public static volatile SingularAttribute<Temporada, Integer> qtd_episodios;
	public static volatile SingularAttribute<Temporada, Integer> ano;
	public static volatile SingularAttribute<Temporada, Integer> numero;
	public static volatile SingularAttribute<Temporada, Serie> serie;
	public static volatile ListAttribute<Temporada, Episodio> episodios;
	public static volatile SingularAttribute<Temporada, Long> id;
	public static volatile SingularAttribute<Temporada, Float> avaliacao;

	public static final String QTD_EPISODIOS = "qtd_episodios";
	public static final String ANO = "ano";
	public static final String NUMERO = "numero";
	public static final String SERIE = "serie";
	public static final String EPISODIOS = "episodios";
	public static final String ID = "id";
	public static final String AVALIACAO = "avaliacao";

}

