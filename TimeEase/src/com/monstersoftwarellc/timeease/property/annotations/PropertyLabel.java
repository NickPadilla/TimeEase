/**
 * 
 */
package com.monstersoftwarellc.timeease.property.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author navid
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PropertyLabel {

	/**
	 * The label to display for the property
	 */
	String value();
	
}
