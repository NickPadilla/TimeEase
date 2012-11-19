/**
 * 
 */
package com.monstersoftwarellc.timeease.repository.impl;

import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.model.impl.Property;
import com.monstersoftwarellc.timeease.repository.IRepository;

/**
 * @author nicholas
 *
 */
public interface PropertyRepository extends IRepository<Property> {
	
	Property findOneByPropertyNameAndCreatedBy(String propertyName, Account createdBy);

}
