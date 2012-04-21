/**
 * 
 */
package com.monstersoftwarellc.timeease.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.monstersoftwarellc.timeease.model.client.Client;

/**
 * @author nicholas
 *
 */
@Repository
@Transactional
public class ClientDAO extends AbstractDAO<Client> implements IClientDAO {
	
	private static final Logger LOG = Logger.getLogger(ClientDAO.class);

	public ClientDAO() {
		super(Client.class);
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.dao.AbstractDAO#getLog()
	 */
	@Override
	protected Logger getLog() {
		return LOG;
	}

}
