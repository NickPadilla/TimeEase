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
public @interface ResizeControl {
	
	/**
	 * Specify the desired width of the control.  Default is Time Ease standard <br/>
	 * which is 150. 
	 * @return width or default of 150
	 */
	int width() default 150;
	
	/**
	 * Specify the desired height of the control.  Default is Time Ease standard <br/>
	 * which is also SWT.DEFAULT, -1.
	 * @return height or deafult of -1.
	 */
	int height() default -1;

}
