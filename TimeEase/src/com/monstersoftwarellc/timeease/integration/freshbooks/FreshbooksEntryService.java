/**
 * 
 */
package com.monstersoftwarellc.timeease.integration.freshbooks;

import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;

import org.eclipse.core.runtime.Assert;

import com.monstersoftwarellc.timeease.integration.IEntryService;
import com.monstersoftwarellc.timeease.model.entry.Entry;
import com.monstersoftwarellc.timeease.model.entry.EntryRequest;
import com.monstersoftwarellc.timeease.model.entry.EntryResponse;
import com.monstersoftwarellc.timeease.model.enums.RequestMethods;
import com.monstersoftwarellc.timeease.model.enums.ResponseStatus;
import com.monstersoftwarellc.timeease.utility.ConnectionUtility;

/**
 * @author nicholas
 *
 */
public class FreshbooksEntryService implements IEntryService {
	
	private static FreshbooksEntryService service = null;
	
	/**
	 * 
	 */
	private FreshbooksEntryService() {}
	
	public static FreshbooksEntryService getCurrentInstance(){
		if(service == null){
			service = new FreshbooksEntryService();
		}
		return service;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.integration.IIntegrationService#create(com.monstersoftwarellc.timeease.integration.IFreshbooksEntity)
	 */
	@Override
	public boolean create(Entry item) throws AuthenticationException, IllegalStateException, ConfigurationException {
		Assert.isTrue(item.getExternalId() == null || item.getExternalId() == 0);
		boolean sent = true;
		if(ConnectionUtility.areWeOnline()){
			item.setExternalId(null);
			item.setDirty(null);
			EntryRequest request = new EntryRequest();
			request.setEntity(item);
			request.setMethod(RequestMethods.CREATE);
			EntryResponse response = request.getResponse();
			if(response != null && response.getStatus() == ResponseStatus.SUCCESSFUL){
				item.setExternalId(response.getId());
				item.setBilled(0);
				item.setEntryDate(item.getStartTime() != null ? item.getStartTime() : item.getEntryDate());
			}else{
				sent = false;
				item.setEntryDate(item.getStartTime());
			}
		}else{
			sent = false;
			item.setEntryDate(item.getStartTime());
		}
		
		return sent;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.integration.IIntegrationService#getById(int)
	 */
	@Override
	public Entry getById(int id) {
		try {
			throw new Exception("Method not filled in, please setup me up first!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// first check to see if we have the entry in memory
		
		// then check to see if we have it on disk
		
		// if we don't have it in xml or memory check to see if we are online
		// if we are pull from freshbooks and add to xml and memory
		// not sure if this will ever happen but better have a plan now.
		return null;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.integration.IIntegrationService#update(com.monstersoftwarellc.timeease.integration.IFreshbooksEntity)
	 */
	@Override
	public boolean update(Entry item) throws AuthenticationException, IllegalStateException, ConfigurationException {
		Assert.isTrue(item.getExternalId() != null && item.getExternalId() != 0);
		boolean success = false;
		if(ConnectionUtility.areWeOnline()){
			item.setDirty(null);
			// can't update billed as it is read only
			item.setBilled(null);
			EntryRequest request = new EntryRequest();
			request.setMethod(RequestMethods.UPDATE);
			request.setEntity(item);
			EntryResponse response = request.getResponse();
			if(response != null && response.getStatus() == ResponseStatus.SUCCESSFUL){
				success = true;
			}
		}
		// if we couldn't update then set entry dirty
		if(!success){
			item.setDirty(true);
		}
		
		return success;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.integration.IIntegrationService#delete(com.monstersoftwarellc.timeease.integration.IFreshbooksEntity)
	 */
	@Override
	public Entry delete(Entry item) {
		try {
			throw new Exception("Method not filled in, please setup me up first!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.integration.IIntegrationService#getAll()
	 */
	@Override
	public List<Entry> getAll() {
		try {
			throw new Exception("Method not filled in, please setup me up first!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
