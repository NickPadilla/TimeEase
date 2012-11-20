/**
 * 
 */
package com.monstersoftwarellc.timeease.property.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provide field name that will be the parent of the property this is on. 
 * @author nicholas
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PropertyDependent {
	
	String value();
}
