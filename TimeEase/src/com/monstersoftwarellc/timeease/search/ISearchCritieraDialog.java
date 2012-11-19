/**
 * 
 */
package com.monstersoftwarellc.timeease.search;

/**
 * @author nicholas
 *
 */
public interface ISearchCritieraDialog {

	/**
	 * Returns the {@link ISearchCritiera} needed for this dialog.
	 * @return
	 */
	public abstract ISearchCritiera<?> getCriteria();
	
}
