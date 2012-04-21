/**
 * 
 */
package com.monstersoftwarellc.timeease.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.monstersoftwarellc.timeease.model.impl.ApplicationSettings;

/**
 * @author nicholas
 *
 */
@Repository
@Transactional
public class ApplicationSettingsDAO extends AbstractDAO<ApplicationSettings> implements IApplicationSettingsDAO {

	private static Logger LOG = Logger.getLogger(ApplicationSettingsDAO.class);


	/**
	 * @param daoImplClass
	 */
	public ApplicationSettingsDAO() {
		super(ApplicationSettings.class);
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.AbstractDAO#getLog()
	 */
	@Override
	protected Logger getLog() {
		return LOG;
	}

}
