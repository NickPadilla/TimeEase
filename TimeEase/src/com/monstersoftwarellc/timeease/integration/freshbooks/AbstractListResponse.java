/**
 * 
 */
package com.monstersoftwarellc.timeease.integration.freshbooks;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import com.monstersoftwarellc.timeease.integration.IFreshbooksEntity;

/**
 * Abstract class that covers most List Response functionality.  <br/>
 * Subclasses should only need to contain a {@link List} of <br/>
 * {@link IFreshbooksEntity} objects. Also when subclasses are created<br/>
 * they MUST set the <code>freshbooksEntity</code>.<br/>
 * TODO: Find a better way of doing this.
 * @author nick
 *
 */
public abstract class AbstractListResponse extends AbstractList {
	
	private Integer pages;
	
	private Integer total;
	
	private IFreshbooksEntity freshbooksEntity;
	
	
	/**
	 * @return the freshbooksEntity
	 */
	@XmlTransient
	public IFreshbooksEntity getFreshbooksEntity(){
		return this.freshbooksEntity;
	}
	
	/**
	 * @param freshbooksEntity
	 */
	public void setFreshbooksEntity(IFreshbooksEntity freshbooksEntity){
		this.freshbooksEntity = freshbooksEntity;
	}
	

	/**
	 * @return the pages
	 */
	@XmlAttribute(name="pages")
	public Integer getPages() {
		return pages;
	}

	/**
	 * @param pages the pages to set
	 */
	public void setPages(Integer pages) {
		this.pages = pages;
	}

	/**
	 * @return the total
	 */
	@XmlAttribute(name="total")
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

}
