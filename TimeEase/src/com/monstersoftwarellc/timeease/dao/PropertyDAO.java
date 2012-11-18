/**
 * 
 */
package com.monstersoftwarellc.timeease.dao;

import java.util.ArrayList;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.monstersoftwarellc.timeease.model.WhereClause;
import com.monstersoftwarellc.timeease.model.impl.Property;
import com.monstersoftwarellc.timeease.service.ISecurityService;

/**
 * @author navid
 *
 */
@Repository
@Transactional
public class PropertyDAO extends AbstractDAO<Property> implements IPropertyDAO {

	private static Logger LOG = Logger.getLogger(PropertyDAO.class);
	
	@Autowired
	private ISecurityService securityService;
	
	public PropertyDAO() {
		super(Property.class);
	}

	/* (non-Javadoc)
	 * @see com.goldrush.dao.impl.AbstractDAO#getLog()
	 */
	@Override
	protected Logger getLog() {
		return LOG;
	}



	/* (non-Javadoc)
	 * @see com.goldrush.dao.IPropertyDAO#findByName(java.lang.String)
	 */
	@Override
	public Property findByName(String name) throws NonUniqueResultException {
		Property ret = null;
	    try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Property> query = cb.createQuery(Property.class);
			Root<Property> root = query.from(Property.class);
			
			//query.where(cb.equal(root.get(Property_.propertyName), name));
			//query.where(cb.equal(root.get("createdBy"), securityService.getCurrentlyLoggedInUser()));
			// TODO: move to JPA 2 Meta Model
			ArrayList<WhereClause> opts = new ArrayList<WhereClause>();
			opts.add(new WhereClause("propertyName", name));
			opts.add(new WhereClause("createdBy", securityService.getCurrentlyLoggedInUser()));
			
			addWhereClauses(opts, cb, query, root, true, true);
			
			ret =  entityManager.createQuery(query).getSingleResult();
			
		} catch (NoResultException e) {
		}
	    return ret;
	}

}
