/**
 * 
 */
package com.monstersoftwarellc.timeease.service;

import com.monstersoftwarellc.timeease.repository.impl.EntryRepository;

/**
 * @author nicholas
 *
 */
public interface IEntryService {

	public abstract EntryRepository getEntryRepository();
}
