/**
 * 
 */
package com.monstersoftwarellc.timeease.property.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to annotate individual property methods of a given intreface to overrride shared state.
 * If shared the property will be synchronized with the central server.
 * @author navid
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PropertyShared {

	boolean value() default true;
	
}
