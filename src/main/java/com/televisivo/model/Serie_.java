package com.televisivo.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Serie.class)
public abstract class Serie_ {

	public static volatile SingularAttribute<Serie, Integer> restricao;
	public static volatile ListAttribute<Serie, Artista> artistas;
	public static volatile SingularAttribute<Serie, Integer> ano;
	public static volatile SingularAttribute<Serie, Integer> qtd_seguidores;
	public static volatile ListAttribute<Serie, Temporada> temporadas;
	public static volatile ListAttribute<Serie, Elenco> elencos;
	public static volatile ListAttribute<Serie, Categoria> categorias;
	public static volatile SingularAttribute<Serie, String> nome;
	public static volatile SingularAttribute<Serie, Long> id;
	public static volatile SingularAttribute<Serie, String> enredo;
	public static volatile SingularAttribute<Serie, Integer> qtd_temporadas;
	public static volatile SingularAttribute<Serie, Servico> servico;

	public static final String RESTRICAO = "restricao";
	public static final String ARTISTAS = "artistas";
	public static final String ANO = "ano";
	public static final String QTD_SEGUIDORES = "qtd_seguidores";
	public static final String TEMPORADAS = "temporadas";
	public static final String ELENCOS = "elencos";
	public static final String CATEGORIAS = "categorias";
	public static final String NOME = "nome";
	public static final String ID = "id";
	public static final String ENREDO = "enredo";
	public static final String QTD_TEMPORADAS = "qtd_temporadas";
	public static final String SERVICO = "servico";

}

