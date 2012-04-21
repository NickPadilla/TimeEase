/**
 * 
 */
package com.monstersoftwarellc.timeease.integration;

import com.monstersoftwarellc.timeease.integration.freshbooks.AbstractListResponse;
import com.monstersoftwarellc.timeease.model.enums.RequestMethods;



/**
 * Interface that declares needed Freshbooks Response functions. <br/>
 * Concrete classes should provide the needed <code>id</code>, <br/>
 * <code>responseEntity</code> and <code>listResponseEntities</code> objects.
 * @author nick
 *
 */
public interface IResponse {
	
	/**
	 * Returns the <code>id</code> of the {@link IFreshbooksEntity} object that has been created, <br/>
	 * {@link RequestMethods}.CREATE. This field is used for create operations only, since that is <br/>
	 * the only time it will be populated. Concrete classes must specify the correct XML field name <br/>
	 * so that jaxb correctly finds it.
	 * @return
	 */
	public abstract Long getId();
	
	/**
	 * Returns a fully constructed and concrete {@link IFreshbooksEntity} object.  This is returned when <br/>
	 * the {@link RequestMethods}.GET operation is called. Concrete Classes must specify the correct<br/>
	 * XML field name for the object so that jaxb can correctly unmarshal the entity.
	 * @return
	 */
	public abstract IFreshbooksEntity getResponseEntity();
	
	/**
	 * Returns a fully constructed and concrete {@link AbstractListResponse} that will <br/>
	 * Contain the list of {@link IFreshbooksEntity}'s. 
	 * @return
	 */
	public abstract Object getListResponseEntities();

}
