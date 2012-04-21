/**
 * 
 */
package com.monstersoftwarellc.timeease.integration.freshbooks;

import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;

import com.monstersoftwarellc.timeease.integration.IClientService;
import com.monstersoftwarellc.timeease.model.client.Client;
import com.monstersoftwarellc.timeease.model.client.ClientRequest;
import com.monstersoftwarellc.timeease.model.enums.ClientStatus;
import com.monstersoftwarellc.timeease.model.enums.RequestMethods;

/**
 * @author nicholas
 *
 */
public class FreshbooksClientService implements IClientService {

	private static FreshbooksClientService service = null;

	private FreshbooksClientService(){}

	public static FreshbooksClientService getCurrentInstance(){
		if(service == null){
			service = new FreshbooksClientService();
		}
		return service;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.integration.IIntegrationService#create(com.monstersoftwarellc.timeease.integration.IFreshbooksEntity)
	 */
	@Override
	public boolean create(Client item) {
		try {
			throw new Exception("Method not filled in, please setup me up first!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.integration.IIntegrationService#getById(int)
	 */
	@Override
	public Client getById(int id) throws AuthenticationException, IllegalStateException, ConfigurationException {
		Client client = null;
		Client temp = new Client();
		temp.setExternalId(id);
		ClientRequest request = new ClientRequest();
		request.setMethod(RequestMethods.GET);
		request.setEntity(temp);
		client = request.getResponse().getResponseEntity();

		return client;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.integration.IIntegrationService#update(com.monstersoftwarellc.timeease.integration.IFreshbooksEntity)
	 */
	@Override
	public boolean update(Client item) {
		try {
			throw new Exception("Method not filled in, please setup me up first!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.integration.IIntegrationService#delete(com.monstersoftwarellc.timeease.integration.IFreshbooksEntity)
	 */
	@Override
	public Client delete(Client item) {
		try {
			throw new Exception("Method not filled in, please setup me up first!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.integration.IClientService#getClients(com.monstersoftwarellc.timeease.model.enums.ClientStatus)
	 */
	@Override
	public List<Client> getClients(ClientStatus statuses) throws AuthenticationException, IllegalStateException, ConfigurationException {		
		ClientRequest request = new ClientRequest();
		request.setStatus(statuses);
		request.setMethod(RequestMethods.LIST);
		request.setEntity(new Client());
		return request.getResponse().getListResponseEntities().getClientList();
	}


	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.integration.IIntegrationService#getAll()
	 */
	@Override
	public List<Client> getAll() throws AuthenticationException, IllegalStateException, ConfigurationException {
		List<Client> clients = new ArrayList<Client>();	
		int page = 1;
		boolean haveAll = false;
		while(!haveAll){
			// TODO: here we need to get them from freshbooks
			List<Client> temp = getList(100, page);
			clients.addAll(temp);
			// if we have under 100 or over 100 we are finished.
			// if we have over 100 we had to load from disk
			if(temp.size() < 100 || temp.size() > 100){
				haveAll = true;
			}
			page++;
		}
		return clients;
	}

	/**
	 * @param numb
	 * @param page
	 * @return
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	private List<Client> getList(int numb, int page) throws AuthenticationException, IllegalStateException, ConfigurationException{
		ClientRequest request = new ClientRequest();
		request.setItemsPerPage(numb);
		request.setPage(page);
		request.setMethod(RequestMethods.LIST);
		request.setEntity(new Client());
		return request.getResponse().getListResponseEntities().getClientList();
	}

}
