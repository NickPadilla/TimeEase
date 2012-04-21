/**
 * 
 */
package com.monstersoftwarellc.timeease.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation will only be used on fields.  You only need to annotate the SWT form objects.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={java.lang.annotation.ElementType.METHOD})
public @interface RequiredForNew {

}
