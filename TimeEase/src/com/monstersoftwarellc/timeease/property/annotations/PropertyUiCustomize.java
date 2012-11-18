/**
 * 
 */
package com.monstersoftwarellc.timeease.property.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows the dynamic ui to be customized at run time. 
 * LOOK: This feels extremely hacky but I need this functionality now. 
 * 
 * Maybe it makes more sense to provide a "Customization Handler" object to this sort of thing versus string expressions.
 * I maybe getting a little crazy with this whole SPEL thing.  
 * 
 * @author navid
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PropertyUiCustomize {

	/**
	 * An array of expressions to execute.
	 * The UI object can be accessed with the variable #ui 
	 * NOTE: the type of control created changes depending on the property object type.
	 * @return
	 */
	String[] value();
	
}
