/**
 * 
 */
package com.monstersoftwarellc.timeease.utility;

import org.springframework.data.domain.Sort.Direction;

import com.monstersoftwarellc.timeease.enums.OrderType;
import com.monstersoftwarellc.timeease.property.IApplicationSettings;
import com.monstersoftwarellc.timeease.service.impl.ServiceLocator;



/**
 * @author nicholas
 *
 */
public class SpecificationUtility {

	public static String getLikePattern(final String searchTerm) {
		StringBuilder pattern = new StringBuilder();
		pattern.append(searchTerm.toLowerCase());
		pattern.append("%");
		return pattern.toString();
	}
	
	public static Direction getSortDirection(){
		Direction dir = null;
		if(ServiceLocator.locateCurrent(IApplicationSettings.class).getDefaultSortOrder() == OrderType.ASC){
			dir = Direction.ASC;
		}else{
			dir = Direction.DESC;
		}
		return dir;
	}
}
