package com.monstersoftwarellc.timeease.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.monstersoftwarellc.timeease.model.impl.Project;

/**
 * @author nicholas
 *
 */
@Repository
@Transactional
public class ProjectDAO extends AbstractDAO<Project> implements IProjectDAO {

	private static final Logger LOG = Logger.getLogger(ProjectDAO.class);
	
	public ProjectDAO() {
		super(Project.class);
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.dao.AbstractDAO#getLog()
	 */
	@Override
	protected Logger getLog() {
		return LOG;
	}

	
}
