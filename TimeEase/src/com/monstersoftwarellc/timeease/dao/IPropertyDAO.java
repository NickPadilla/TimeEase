/**
 * 
 */
package com.monstersoftwarellc.timeease.dao;

import javax.persistence.NonUniqueResultException;

import com.monstersoftwarellc.timeease.model.impl.Property;

/**
 * @author navid
 *
 */
public interface IPropertyDAO extends IDAO<Property> {

	/**
	 * Find a property by name.
	 * @param name
	 * @return
	 * @throws NonUniqueResultException if no unique result is found. 
	 */
	public Property findByName(String name) throws NonUniqueResultException;
	
}
