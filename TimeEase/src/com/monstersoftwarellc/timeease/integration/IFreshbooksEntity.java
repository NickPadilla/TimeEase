/**
 * 
 */
package com.monstersoftwarellc.timeease.integration;

import com.monstersoftwarellc.timeease.model.IModelObject;



/**
 * This interface will mark any model objects as being freshbooks objects. <br/>
 * It only defines, right now, the name and id of the object that freshbooks expects.
 * @author nick
 *
 */
public interface IFreshbooksEntity extends IModelObject {
	
	/**
	 * This method will pass back the correct name that freshbooks expects when
	 * sending method in <request> tag.
	 * @return
	 */
	public abstract String getFreshbooksObjectName();
	
	/**
	 * Will pass back the unique object identifier that freshbooks has given to <br/>
	 * the object. <br/>
	 * <b>Note: If no identifier has been assigned you will have a value of 0.</b>
	 * @return
	 */
	public abstract Integer getFreshbooksObjectId();
	
	
	/**
	 * Will pass back the GUI name of the object.
	 * @return
	 */
	public abstract String getFreshbooksObjectLabel();

}
