package com.monstersoftwarellc.timeease.property;

import java.util.List;

import com.monstersoftwarellc.timeease.enums.OrderType;
import com.monstersoftwarellc.timeease.integration.IntegrationType;
import com.monstersoftwarellc.timeease.model.impl.Client;
import com.monstersoftwarellc.timeease.model.impl.Project;
import com.monstersoftwarellc.timeease.model.impl.Task;
import com.monstersoftwarellc.timeease.property.annotations.PropertyChoice;
import com.monstersoftwarellc.timeease.property.annotations.PropertyDefault;
import com.monstersoftwarellc.timeease.property.annotations.PropertyDependent;
import com.monstersoftwarellc.timeease.property.annotations.PropertyListType;
import com.monstersoftwarellc.timeease.property.annotations.PropertySequence;
import com.monstersoftwarellc.timeease.property.annotations.PropertyUiCustomize;

public interface IApplicationSettings {
	
	/**
	 * @return the paginateSearchPages
	 */
	@PropertyDefault("false")
	@PropertySequence(10)
	public abstract boolean isPaginateSearchPages();

	@PropertyDefault("false")
	@PropertySequence(20)
	public abstract boolean isIncludeBilledEntries();
	
	/**
	 * @return the numberOfItemsToShowPerPage
	 */
	@PropertySequence(30)
	@PropertyDefault("25")
	@PropertyUiCustomize({"#ui.setMaximum(50)"})
	public abstract int getNumberOfItemsToShowPerPage();

	@PropertySequence(40)
	@PropertyDefault("25")
	@PropertyUiCustomize({"#ui.setMaximum(50)"})
	public abstract int getDefaultEntriesBillable();

	@PropertySequence(50)
	@PropertyDefault("0")
	@PropertyUiCustomize({"#ui.setMaximum(999999)","#ui.setDigits(2)"})
	public abstract int getDefaultRate();
	
	/**
	 * @return the defaultSortOrder
	 */
	@PropertyChoice("T(com.monstersoftwarellc.timeease.enums.OrderType).values()")
	@PropertyDefault("T(com.monstersoftwarellc.timeease.enums.OrderType).ASC")
	@PropertySequence(60)
	public abstract OrderType getDefaultSortOrder();

	@PropertyChoice("@clientRepository.findByCreatedByOrderByFirstNameAsc(@securityService.getCurrentlyLoggedInUser())")
	@PropertySequence(70)
	public abstract Client getDefaultClient();

	@PropertyChoice("@projectRepository.findByCreatedByAndClientOrderByNameAsc(@securityService.getCurrentlyLoggedInUser(), #root.getPropertyValueByMetaName('defaultClient'))")
	@PropertySequence(80)
	@PropertyDependent("defaultClient")
	public abstract Project getDefaultProject();

	@PropertyChoice("#root.getPropertyValueByMetaName('defaultProject') != null ? @projectRepository.findOne(#root.getPropertyValueByMetaName('defaultProject').getId()).getProjectTasks() : null")
	@PropertySequence(90)
	@PropertyDependent("defaultProject")
	public abstract Task getDefaultTask();

	@PropertyChoice("T(com.monstersoftwarellc.timeease.integration.IntegrationType).values()")
	@PropertyListType(IntegrationType.class)
	@PropertySequence(100)
	public abstract List<IntegrationType> getIntegrations();
	
	/**
	 * @return the emailHostOrIp
	 */
	@PropertySequence(110)
	public abstract String getEmailHostOrIp();

	/**
	 * @return the emailUsername
	 */
	@PropertySequence(120)
	public abstract String getEmailUsername();
	
	/**
	 * @return the emailPassword
	 */
	@PropertySequence(130)
	public abstract String getEmailPassword();

	/**
	 * @return the emailSendersAddress
	 */
	@PropertySequence(140)
	public abstract String getEmailSendersAddress();

	/**
	 * @return the emailAddressForBugSubmission
	 */
	@PropertySequence(150)
	public abstract String getEmailAddressForBugSubmission();
	

	public abstract void setPaginateSearchPages(boolean paginateSearchPages);

	public abstract void setNumberOfItemsToShowPerPage(int numberOfItemsToShowPerPage);
	
	public abstract void setDefaultSortOrder(OrderType defaultSortOrder);

	public abstract void setEmailAddressForBugSubmission(String emailAddressForBugSubmission);

	public abstract void setEmailHostOrIp(String emailHostOrIp);

	public abstract void setEmailPassword(String emailPassword);

	public abstract void setEmailSendersAddress(String emailSendersAddress);

	public abstract void setEmailUsername(String emailUsername);

	public abstract void setDefaultRate(int defaultRate);

	public abstract void setDefaultEntriesBillable(int defaultEntriesBillable);

	public abstract void setIncludeBilledEntries(boolean includeBilledEntries);

	public abstract void setIntegrations(List<IntegrationType> integrations);

	public abstract void setDefaultClient(Client defaultClient);

	public abstract void setDefaultProject(Project defaultProject);

	public abstract void setDefaultTask(Task defaultTask);

}