/**
 * 
 */
package com.monstersoftwarellc.timeease.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.monstersoftwarellc.timeease.model.impl.Entry;

/**
 * @author nicholas
 *
 */
@Repository
@Transactional
public class EntryDAO extends AbstractDAO<Entry> implements IEntryDAO {

	private static Logger LOG = Logger.getLogger(EntryDAO.class);
	
	/**
	 * 
	 */
	public EntryDAO() {
		super(Entry.class);
	}


	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.dao.AbstractDAO#getLog()
	 */
	@Override
	protected Logger getLog() {
		return LOG;
	}

}
