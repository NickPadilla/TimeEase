/**
 * 
 */
package com.monstersoftwarellc.timeease.property.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
 * @author nicholas
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PropertyListType {

	/**
	 * Provides ability to specify the type of objects inside a {@link List} property.
	 * @return
	 */
	Class<?> value();
}