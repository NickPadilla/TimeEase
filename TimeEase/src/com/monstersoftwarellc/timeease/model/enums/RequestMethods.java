/**
 * 
 */
package com.monstersoftwarellc.timeease.model.enums;

import com.monstersoftwarellc.timeease.integration.IFreshbooksEntity;

/**
 * Enum for centralizing all Freshbooks Request Methods.
 * @author nick
 *
 */
public enum RequestMethods {
	
	CREATE(".create"),
	UPDATE(".update"),
	LIST(".list"),
	GET(".get"),
	DELETE(".delete");
	
	private RequestMethods(String action){
		this.action = action;
	}
	
	private final String action;

	/**
	 * Gets just the action portion, e.g. '.list'.
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Use this method to get the correct Freshbooks method string for the given {@link IFreshbooksEntity}.
	 * @param requestedObject
	 * @return
	 */
	public String createFullyQualifiedMethodAction(IFreshbooksEntity requestedObject){
		return requestedObject.getFreshbooksObjectName() + this.getAction();
	}
}
