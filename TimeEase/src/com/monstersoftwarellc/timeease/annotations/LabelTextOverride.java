/**
 * 
 */
package com.monstersoftwarellc.timeease.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author nick
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={java.lang.annotation.ElementType.METHOD})
public @interface LabelTextOverride {
	
	/**
	 * This field will be what the user will see as the field label. If no label is present we will use the name of the field.
	 * @return
	 */
	String label() default "";
	
	/**
	 * This value will allow you to specify that a label not be required for the given field. 
	 * @return
	 */
	boolean labelIgnore() default false;

	/**
	 * Allows user to specify a tooltip for the given control. Default is a blank string.
	 * @return
	 */
	String toolTip() default "";

}
