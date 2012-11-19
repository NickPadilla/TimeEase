/**
 * 
 */
package com.monstersoftwarellc.timeease.repository.impl;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.monstersoftwarellc.timeease.model.impl.Entry;
import com.monstersoftwarellc.timeease.repository.IRepository;

/**
 * @author nicholas
 *
 */
public interface EntryRepository extends IRepository<Entry> {

	@Query("select e from Entry e where e.startTime is not null and e.endTime is null order by e.entryDate")
	List<Entry> findIncompleteEntries();
}
