/**
 * 
 */
package com.monstersoftwarellc.timeease.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import com.monstersoftwarellc.timeease.enums.SearchOperators;
import com.monstersoftwarellc.timeease.model.AbstractPropertChangeSupport;
import com.monstersoftwarellc.timeease.model.impl.Entry;
import com.monstersoftwarellc.timeease.model.impl.Entry_;
import com.monstersoftwarellc.timeease.repository.specification.EntrySpecifications;
import com.monstersoftwarellc.timeease.utility.SpecificationUtility;

/**
 * @author nicholas
 *
 */
public class EntrySearchCritieria extends AbstractPropertChangeSupport implements ISearchCritiera<Entry> {

	private SearchOperators entryDateOperator;
	private Date entryDate;
	private SearchOperators hoursOperator;
	private Double hours;


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
	public Double getHours() {
		return hours;
	}


	/**
	 * @param hours the hours to set
	 */
	public void setHours(Double hours) {
		this.hours = hours;
	}


	@Override
	public String getLabel() {
		return "Entry Search";
	}


	@Override
	public Specifications<Entry> getQuerySpecifications() {
		List<Specification<Entry>> entrySpecs = new ArrayList<Specification<Entry>>();
		
		if (entryDate != null) {
			entrySpecs.add(EntrySpecifications.searchForEntryDate(entryDate, entryDateOperator));
		}
		
		if (hours != null) {
			entrySpecs.add(EntrySpecifications.searchForHours(hours, hoursOperator));
		}
		
		// now build out our query
		Specifications<Entry> spec = null;
		for(Specification<Entry> s : entrySpecs){
				if(spec == null){
					spec = Specifications.where(s);
				}else{
					spec = spec.and(s);
				}
		}

		return spec;
	}


	@Override
	public Sort getSort() {
		Sort sort = new Sort(SpecificationUtility.getSortDirection(), Entry_.entryDate.getName());
		sort.and(new Sort(Entry_.hours.getName()));
		return sort;
	}

}