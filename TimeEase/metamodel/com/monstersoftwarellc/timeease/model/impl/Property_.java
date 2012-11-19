package com.monstersoftwarellc.timeease.model.impl;

import com.monstersoftwarellc.timeease.model.BaseDomain_;
import com.monstersoftwarellc.timeease.model.PropertyType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.4.1.v20121003-rNA", date="2012-11-18T21:10:27")
@StaticMetamodel(Property.class)
public class Property_ extends BaseDomain_ {

    public static volatile SingularAttribute<Property, String> propertyName;
    public static volatile SingularAttribute<Property, PropertyType> propertyType;
    public static volatile SingularAttribute<Property, Boolean> propertyHidden;
    public static volatile SingularAttribute<Property, byte[]> propertyValue;
    public static volatile SingularAttribute<Property, byte[]> propertyChoice;
    public static volatile SingularAttribute<Property, Boolean> propertyShared;
    public static volatile SingularAttribute<Property, String> label;

}