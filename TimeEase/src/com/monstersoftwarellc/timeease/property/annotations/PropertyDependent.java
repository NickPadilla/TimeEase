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
	
	
	/**
	 * The name of the property that is the parent to this one.  The parent property must have a lower sequence number.
	 * 
	 * @return
	 */
	String value();
}
