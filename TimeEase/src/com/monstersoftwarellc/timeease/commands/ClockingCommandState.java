/**
 * 
 */
package com.monstersoftwarellc.timeease.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

import com.monstersoftwarellc.timeease.model.impl.Entry;
import com.monstersoftwarellc.timeease.service.IEntryService;
import com.monstersoftwarellc.timeease.service.impl.ServiceLocator;

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
		List<Entry> entries = ServiceLocator.locateCurrent(IEntryService.class).getEntryRepository().findIncompleteEntries();
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
