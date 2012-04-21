/**
 * 
 */
package com.monstersoftwarellc.timeease.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author nicholas
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={java.lang.annotation.ElementType.METHOD})
public @interface AdditionalWidgetSettings {
	
	boolean isPassword() default false;
	
	int additionalWidgetSettings() default 0;

}
