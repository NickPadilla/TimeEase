/**
 * 
 */
package com.monstersoftwarellc.timeease.repository;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.monstersoftwarellc.timeease.property.IApplicationSettings;
import com.monstersoftwarellc.timeease.search.ISearchCritiera;

/**
 * @author nicholas
 *
 */
public class RepositoryImpl<T> extends SimpleJpaRepository<T, Long> implements IRepository<T>{
	
	 private EntityManager entityManager;

	  // There are two constructors to choose from, either can be used.
	  public RepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
	    super(domainClass, entityManager);
	    // This is the recommended method for accessing inherited class dependencies.
	    this.entityManager = entityManager;
	  }

	/* (non-Javadoc)
	 * @see com.reflogic.upd.repository.IRepository#refresh(java.lang.Object)
	 */
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public Page<T> getSearchListPage(ISearchCritiera<T> searchCriteria, int page, IApplicationSettings settings){
		return findAll(searchCriteria.getQuerySpecifications(), new PageRequest(page, settings.getNumberOfItemsToShowPerPage(), searchCriteria.getSort()));
	}
	
	@Override
	public int getSearchListPageCount(ISearchCritiera<T> searchCriteria, int page, IApplicationSettings settings){
		return getSearchListPage(searchCriteria, page, settings).getSize();
	}

}
