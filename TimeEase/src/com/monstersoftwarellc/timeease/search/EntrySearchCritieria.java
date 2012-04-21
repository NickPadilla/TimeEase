/**
 * 
 */
package com.monstersoftwarellc.timeease.search;

import java.util.Date;

import javax.persistence.OrderBy;

import com.monstersoftwarellc.timeease.annotations.MappedColumnName;
import com.monstersoftwarellc.timeease.enums.SearchOperators;
import com.monstersoftwarellc.timeease.model.AbstractPropertChangeSupport;

/**
 * @author nicholas
 *
 */
public class EntrySearchCritieria extends AbstractPropertChangeSupport implements ISearchCritiera {

	private SearchOperators entryDateOperator;
	@MappedColumnName(requireSearchOperator=true)
	@OrderBy
	private Date entryDate;
	private SearchOperators hoursOperator;
	@MappedColumnName(requireSearchOperator=true)
	@OrderBy
	private Integer hours;


	/**
	 * @return the entryDateOperator
	 */
	public SearchOperators getEntryDateOperator() {
		return entryDateOperator;
	}


	/**
	 * @param entryDateOperator the entryDateOperator to set
	 */
	public void setEntryDateOperator(SearchOperators createdDateOperator) {
		this.entryDateOperator = createdDateOperator;
	}


	/**
	 * @return the entryDate
	 */
	public Date getEntryDate() {
		return entryDate;
	}


	/**
	 * @param entryDate the entryDate to set
	 */
	public void setEntryDate(Date createdDate) {
		this.entryDate = createdDate;
	}


	/**
	 * @return the hoursOperator
	 */
	public SearchOperators getHoursOperator() {
		return hoursOperator;
	}


	/**
	 * @param hoursOperator the hoursOperator to set
	 */
	public void setHoursOperator(SearchOperators totalOperator) {
		this.hoursOperator = totalOperator;
	}


	/**
	 * @return the hours
	 */
	public Integer getHours() {
		return hours;
	}


	/**
	 * @param hours the hours to set
	 */
	public void setHours(Integer hours) {
		this.hours = hours;
	}


	@Override
	public String getLabel() {
		return "Entry Search";
	}

}