package com.monstersoftwarellc.timeease.model.impl;

import com.monstersoftwarellc.timeease.model.BaseDomain_;
import com.monstersoftwarellc.timeease.model.PropertyType;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-20T15:34:40.161-0700")
@StaticMetamodel(Property.class)
public class Property_ extends BaseDomain_ {
	public static volatile SingularAttribute<Property, Account> createdBy;
	public static volatile SingularAttribute<Property, Account> lastModifiedBy;
	public static volatile SingularAttribute<Property, String> propertyName;
	public static volatile SingularAttribute<Property, String> label;
	public static volatile SingularAttribute<Property, byte[]> propertyValue;
	public static volatile SingularAttribute<Property, byte[]> propertyChoice;
	public static volatile SingularAttribute<Property, Boolean> propertyHidden;
	public static volatile SingularAttribute<Property, Boolean> propertyShared;
	public static volatile SingularAttribute<Property, PropertyType> propertyType;
	public static volatile SingularAttribute<Property, Date> createdDate;
	public static volatile SingularAttribute<Property, Date> lastModifiedDate;
	public static volatile SingularAttribute<Property, Long> id;
}
