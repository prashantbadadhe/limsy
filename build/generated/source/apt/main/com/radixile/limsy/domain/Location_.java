package com.radixile.limsy.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Location.class)
public abstract class Location_ {

	public static volatile SingularAttribute<Location, String> area;
	public static volatile SingularAttribute<Location, String> zip;
	public static volatile SingularAttribute<Location, String> country;
	public static volatile SingularAttribute<Location, String> city;
	public static volatile SingularAttribute<Location, Long> id;
	public static volatile SingularAttribute<Location, String> state;

}

