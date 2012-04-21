/**
 * 
 */
package com.monstersoftwarellc.timeease.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

import com.monstersoftwarellc.timeease.dao.IEntryDAO;
import com.monstersoftwarellc.timeease.enums.SearchOperators;
import com.monstersoftwarellc.timeease.model.WhereClause;
import com.monstersoftwarellc.timeease.model.entry.Entry;
import com.monstersoftwarellc.timeease.service.ServiceLocator;

/**
 * @author nicholas
 *
 */
public class ClockingCommandState extends AbstractSourceProvider {
	
	public final static String ID = "com.monstersoftwarellc.timeease.commands.ClockingCommandState";


	/* (non-Javadoc)
	 * @see org.eclipse.ui.ISourceProvider#dispose()
	 */
	@Override
	public void dispose() {

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.ISourceProvider#getCurrentState()
	 */
	@Override
	public Map<?,?> getCurrentState() {
		Map<String,Object> currentValues = new HashMap<String,Object>(1);
		List<WhereClause> clauses = new ArrayList<WhereClause>();
		clauses.add(new WhereClause("startTime", null, SearchOperators.NOT_EQUAL_TO));
		clauses.add(new WhereClause("endTime", null, SearchOperators.EQUAL_TO));
		List<Entry> entries = ServiceLocator.locateCurrent(IEntryDAO.class).findAllOrderBy(clauses, true, "entryDate");
		if(entries != null && !entries.isEmpty()){
			currentValues.put(ID, true);		
		}else{
			currentValues.put(ID, false);		
		}
		return currentValues;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.ISourceProvider#getProvidedSourceNames()
	 */
	@Override
	public String[] getProvidedSourceNames() {
		return new String[] { ID };
	}
	
	public void setIn() {
        fireSourceChanged(ISources.WORKBENCH, ID, true);
    }

    public void setOut() {
        fireSourceChanged(ISources.WORKBENCH, ID, false);
    }

}
