/**
 * 
 */
package com.monstersoftwarellc.timeease.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.monstersoftwarellc.timeease.model.impl.Task;

/**
 * @author nicholas
 *
 */
@Repository
@Transactional
public class TaskDAO extends AbstractDAO<Task> implements ITaskDAO {

	private static final Logger LOG = Logger.getLogger(TaskDAO.class);

	protected TaskDAO() {
		super(Task.class);
	}
	
	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.dao.AbstractDAO#getLog()
	 */
	@Override
	protected Logger getLog() {
		return LOG;
	}

}
