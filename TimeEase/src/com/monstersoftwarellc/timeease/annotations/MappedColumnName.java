/**
 * 
 */
package com.monstersoftwarellc.timeease.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.monstersoftwarellc.timeease.enums.SearchOperators;

/**
 * @author nicholas
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={java.lang.annotation.ElementType.FIELD})
public @interface MappedColumnName {
	
	
	/**
	 * Map a given element to a column name.  By default we use the name of the
	 * field itself.  
	 * @return
	 */
	String columnNameOverride() default "";
	
	/**
	 * This field is required to ensure we look for the {@link SearchOperators} for the field.
	 * By default we don't look for an operator.
	 * @return
	 */
	boolean requireSearchOperator() default false;
	
	
	/**
	 * Optional ability to specify the field name to use when mapping {@link SearchOperators}.
	 * By default we use the name of the field it decorates plus 'Operator' at the end. This field
	 * is ignored if requireSearchOperator is false, which it is by default.
	 * If you use the columnNameOverride you will have to specify this field, unless you name the 
	 * search operator field "columnNameOverride"+Operator.
	 * @return
	 */
	String searchOperatorFieldNameOverride() default "";
	
	/**
	 * If we need to search a column and not worry about case sensitivity.
	 * Default is we take case into account. 
	 * @return
	 */
	boolean caseSensitive() default true;
	
	/**
	 * If we need to perform a like clause on this item.
	 * @return
	 */
	boolean useLikeForWhereClause() default false;

}
