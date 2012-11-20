/**
 * 
 */
package com.monstersoftwarellc.timeease.property;

import java.util.Map;

/**
 * @author navid
 *
 */
public interface ISpelService {

	/**
	 * Parses the given expression and returns the result.
	 * @param expression the expression to parse
	 * @return object the result.
	 */
	public abstract Object getValue(String expression);

	/**
	 * Parses the given expression and returns the result.
	 * @param expression the expression to parse
	 * @param rootObject the desired root object.
	 * @return object the result.
	 */
	public Object getValue(String expression,Object rootObject);
	
	/**
	 * Evaluate the expression and return the result with the expected type.
	 * @param expression the expression to parse
	 * @param expectedResultType the return type expected.
	 * @return T the result.
	 */
	public abstract <T> T getValue(String expression,
			Class<T> expectedResultType);

	/**
	 * Parses the given expression and returns the result.
	 * @param expression the expression to parse
	 * @param variables the values to make available to the expression.
	 * @return object the result.
	 */
	public Object getValue(String expression,Map<String,Object> variables);
	
}