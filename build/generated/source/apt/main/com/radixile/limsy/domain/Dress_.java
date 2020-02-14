package com.radixile.limsy.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Dress.class)
public abstract class Dress_ {

	public static volatile SingularAttribute<Dress, byte[]> image;
	public static volatile SingularAttribute<Dress, LocalDate> purchaseDate;
	public static volatile SingularAttribute<Dress, String> color;
	public static volatile SingularAttribute<Dress, Double> price;
	public static volatile SingularAttribute<Dress, String> imageContentType;
	public static volatile SingularAttribute<Dress, Boolean> inUse;
	public static volatile SingularAttribute<Dress, Long> id;
	public static volatile SingularAttribute<Dress, Category> category;

}

