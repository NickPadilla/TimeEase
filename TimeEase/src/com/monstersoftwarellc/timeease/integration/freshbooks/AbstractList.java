/**
 * 
 */
package com.monstersoftwarellc.timeease.integration.freshbooks;

import javax.xml.bind.annotation.XmlAttribute;

import com.monstersoftwarellc.timeease.integration.IFreshbooksEntity;
import com.monstersoftwarellc.timeease.model.enums.RequestMethods;



/**
 * Base Freshbooks List class, contains data common to all {@link RequestMethods}.LIST<br/>
 * request and response calls for {@link IFreshbooksEntity} objects.
 * @author nick
 *
 */
public abstract class AbstractList {

	private Integer itemsPerPage;
	
	private Integer page;
	
	
	/**
	 * @return the itemsPerPage
	 */
	@XmlAttribute(name="per_page")
	public Integer getItemsPerPage() {
		return itemsPerPage;
	}

	/**
	 * @param itemsPerPage the itemsPerPage to set
	 */
	public void setItemsPerPage(Integer itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	/**
	 * @return the page
	 */
	@XmlAttribute(name="page")
	public Integer getPage() {
		return page;
	}
	
	/**
	 * @param page the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

}
