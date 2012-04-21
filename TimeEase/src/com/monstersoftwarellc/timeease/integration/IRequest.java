package com.monstersoftwarellc.timeease.integration;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;

import com.monstersoftwarellc.timeease.model.enums.RequestMethods;

/**
 * Specifies the contract for any Request objects.
 * @author nick
 *
 * @param <T>
 */
public interface IRequest<T extends IResponse> {
 
	/**
	 * The {@link RequestMethods} type to use for this request.
	 * @return
	 */
	public abstract RequestMethods getMethod();
	
	
	/**
	 * The entity this IRequest is for.
	 * @return
	 */
	public abstract IFreshbooksEntity getEntity();
	
	/**
	 * Passes back an IResponse object for the given entity.
	 * @return
	 * @throws ConfigurationException 
	 * @throws AuthenticationException 
	 * @throws IllegalStateException 
	 */
	public abstract T getResponse() throws IllegalStateException, AuthenticationException, ConfigurationException;
	
}
