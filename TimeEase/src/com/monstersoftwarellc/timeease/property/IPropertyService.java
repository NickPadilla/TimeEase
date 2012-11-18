/**
 * 
 */
package com.monstersoftwarellc.timeease.property;

/**
 * @author navid
 *
 */
public interface IPropertyService {

	/**
	 * Return a dynamic Property Object for the given interface. 
	 * The given class must be an interface.
	 * @param clazz
	 * @return
	 * @throws IllegalArgumentException if the clazz parameter is not an interface. Or the interface methods do not follow standard Java bean naming conventions.
	 */
	public abstract <T> T getProperties(Class<T> clazz);


	
}