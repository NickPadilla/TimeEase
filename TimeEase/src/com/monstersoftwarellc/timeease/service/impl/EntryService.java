/**
 * 
 */
package com.monstersoftwarellc.timeease.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monstersoftwarellc.timeease.repository.impl.EntryRepository;
import com.monstersoftwarellc.timeease.service.IEntryService;

/**
 * @author nicholas
 *
 */
@Service
public class EntryService implements IEntryService {
	
	@Autowired
	private EntryRepository entryRepository;

	@Override
	public EntryRepository getEntryRepository() {
		return entryRepository;
	}

}
