/**
 * 
 */
package com.monstersoftwarellc.timeease.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monstersoftwarellc.timeease.model.impl.Property;
import com.monstersoftwarellc.timeease.property.IApplicationSettings;
import com.monstersoftwarellc.timeease.property.IPropertyService;
import com.monstersoftwarellc.timeease.repository.impl.PropertyRepository;
import com.monstersoftwarellc.timeease.service.ISecurityService;
import com.monstersoftwarellc.timeease.service.ISettingsService;

/** 
 * 
 * @author nicholas
 *
 */
@Service
public class SettingsService implements ISettingsService {
	
	@Autowired
	private IPropertyService propertyService;

	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private ISecurityService securityService;

	@Override
	public IApplicationSettings getApplicationSettings() {
		return propertyService.getProperties(IApplicationSettings.class);
	}

	@Override
	public PropertyRepository getPropertyRepository() {
		return propertyRepository;
	}

	@Override
	public Property findByName(String name) {
		return propertyRepository.findOneByPropertyNameAndCreatedBy(name, securityService.getCurrentlyLoggedInUser());
	}

}
