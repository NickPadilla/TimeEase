package com.monstersoftwarellc.timeease.model.impl;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.monstersoftwarellc.timeease.enums.ILabel;
import com.monstersoftwarellc.timeease.enums.OrderType;
import com.monstersoftwarellc.timeease.integration.AbstractIntegration;
import com.monstersoftwarellc.timeease.integration.Integration;
import com.monstersoftwarellc.timeease.model.AbstractModelInput;
import com.monstersoftwarellc.timeease.model.client.Client;
import com.monstersoftwarellc.timeease.model.project.Project;
import com.monstersoftwarellc.timeease.model.task.Task;

@Entity
public class ApplicationSettings extends AbstractModelInput implements ILabel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3919598577939781345L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private OrderType defaultSortOrder;
	private boolean paginateSearchPages = false;
	private int numberOfItemsToShowPerPage = 0;
	private int defaultRate; 
	private int defaultEntriesBillable;
	private boolean includeBilledEntries;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY, targetEntity = AbstractIntegration.class)
	private List<Integration> integrations;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Client defaultClient;
	@ManyToOne(fetch=FetchType.LAZY)
	private Project defaultProject;
	@ManyToOne(fetch=FetchType.LAZY)
	private Task defaultTask;
	

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the defaultSortOrder
	 */
	public OrderType getDefaultSortOrder() {
		return defaultSortOrder;
	}

	/**
	 * @param defaultSortOrder the defaultSortOrder to set
	 */
	public void setDefaultSortOrder(OrderType defaultSortOrder) {
		this.defaultSortOrder = defaultSortOrder;
	}

	/**
	 * @return the paginateSearchPages
	 */
	public boolean isPaginateSearchPages() {
		return paginateSearchPages;
	}

	/**
	 * @param paginateSearchPages the paginateSearchPages to set
	 */
	public void setPaginateSearchPages(boolean paginateSearchPages) {
		this.paginateSearchPages = paginateSearchPages;
	}

	/**
	 * @return the numberOfItemsToShowPerPage
	 */
	public int getNumberOfItemsToShowPerPage() {
		return numberOfItemsToShowPerPage;
	}

	/**
	 * @param numberOfItemsToShowPerPage the numberOfItemsToShowPerPage to set
	 */
	public void setNumberOfItemsToShowPerPage(int numberOfItemsToShowPerPage) {
		this.numberOfItemsToShowPerPage = numberOfItemsToShowPerPage;
	}

	/**
	 * @return the defaultRate
	 */
	public int getDefaultRate() {
		return defaultRate;
	}

	/**
	 * @param defaultRate the defaultRate to set
	 */
	public void setDefaultRate(int defaultRate) {
		this.defaultRate = defaultRate;
	}

	/**
	 * @return the defaultEntriesBillable
	 */
	public int getDefaultEntriesBillable() {
		return defaultEntriesBillable;
	}

	/**
	 * @param defaultEntriesBillable the defaultEntriesBillable to set
	 */
	public void setDefaultEntriesBillable(int defaultEntriesBillable) {
		this.defaultEntriesBillable = defaultEntriesBillable;
	}

	/**
	 * @return the includeBilledEntries
	 */
	public boolean isIncludeBilledEntries() {
		return includeBilledEntries;
	}

	/**
	 * @param includeBilledEntries the includeBilledEntries to set
	 */
	public void setIncludeBilledEntries(boolean includeBilledEntries) {
		this.includeBilledEntries = includeBilledEntries;
	}

	/**
	 * @return the integrations
	 */
	public List<Integration> getIntegrations() {
		return integrations;
	}

	/**
	 * @param integrations the integrations to set
	 */
	public void setIntegrations(List<Integration> integrations) {
		this.integrations = integrations;
	}

	/**
	 * @return the defaultClient
	 */
	public Client getDefaultClient() {
		return defaultClient;
	}

	/**
	 * @param defaultClient the defaultClient to set
	 */
	public void setDefaultClient(Client defaultClient) {
		this.defaultClient = defaultClient;
	}

	/**
	 * @return the defaultProject
	 */
	public Project getDefaultProject() {
		return defaultProject;
	}

	/**
	 * @param defaultProject the defaultProject to set
	 */
	public void setDefaultProject(Project defaultProject) {
		this.defaultProject = defaultProject;
	}

	/**
	 * @return the defaultTask
	 */
	public Task getDefaultTask() {
		return defaultTask;
	}

	/**
	 * @param defaultTask the defaultTask to set
	 */
	public void setDefaultTask(Task defaultTask) {
		this.defaultTask = defaultTask;
	}

	@Override
	public String getLabel() {
		return "Application Settings";
	}
}
