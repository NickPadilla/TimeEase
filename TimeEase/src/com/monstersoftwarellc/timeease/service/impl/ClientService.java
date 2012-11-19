/**
 * 
 */
package com.monstersoftwarellc.timeease.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monstersoftwarellc.timeease.repository.impl.ClientRepository;
import com.monstersoftwarellc.timeease.service.IClientService;

/**
 * @author nicholas
 *
 */
@Service
public class ClientService implements IClientService {

	@Autowired
	private ClientRepository clientRepository;

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.service.IClientService#getClientRepository()
	 */
	@Override
	public ClientRepository getClientRepository() {
		return clientRepository;
	}

}
