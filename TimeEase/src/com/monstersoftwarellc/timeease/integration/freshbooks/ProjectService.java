/**
 * 
 */
package com.monstersoftwarellc.timeease.integration.freshbooks;

import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;

import com.monstersoftwarellc.timeease.integration.IProjectService;
import com.monstersoftwarellc.timeease.model.client.Client;
import com.monstersoftwarellc.timeease.model.enums.RequestMethods;
import com.monstersoftwarellc.timeease.model.project.Project;
import com.monstersoftwarellc.timeease.model.project.ProjectRequest;
import com.monstersoftwarellc.timeease.model.project.ProjectResponse;

/**
 * @author nicholas
 * 
 */
public class ProjectService implements IProjectService {

	private static ProjectService service = null;

	private ProjectService() {
	}

	public static ProjectService getCurrentInstance() {
		if (service == null) {
			service = new ProjectService();
		}
		return service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.monstersoftwarellc.timeease.service.IProjectService#getProjectsForClient
	 * (int)
	 */
	@Override
	public List<Project> getProjectsForClient(Client client) throws AuthenticationException, IllegalStateException, ConfigurationException {
		List<Project> projects = new ArrayList<Project>();
		ProjectRequest request = new ProjectRequest();
		request.setItemsPerPage(100);
		request.setPage(1);
		request.setClientId(client.getExternalId());
		request.setMethod(RequestMethods.LIST);
		request.setEntity(new Project());
		projects = request.getResponse().getListResponseEntities().getProjectList();

		return projects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.monstersoftwarellc.timeease.service.IFreshBooksService#create(com.
	 * monstersoftwarellc.timeease.model.IFreshbooksEntity)
	 */
	@Override
	public boolean create(Project item) {
		try {
			throw new Exception(
					"Method not filled in, please setup me up first!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.monstersoftwarellc.timeease.service.IFreshBooksService#getById(int)
	 */
	@Override
	public Project getById(int id) throws AuthenticationException, IllegalStateException, ConfigurationException {
		Project project = null;

		Project temp = new Project();
		temp.setExternalId(id);
		ProjectRequest request = new ProjectRequest();
		request.setMethod(RequestMethods.GET);
		request.setEntity(temp);
		ProjectResponse response = request.getResponse();
		project = response.getResponseEntity();

		return project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.monstersoftwarellc.timeease.service.IFreshBooksService#update(com.
	 * monstersoftwarellc.timeease.model.IFreshbooksEntity)
	 */
	@Override
	public boolean update(Project item) {
		try {
			throw new Exception(
					"Method not filled in, please setup me up first!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.monstersoftwarellc.timeease.service.IFreshBooksService#delete(com.
	 * monstersoftwarellc.timeease.model.IFreshbooksEntity)
	 */
	@Override
	public Project delete(Project item) {
		try {
			throw new Exception(
					"Method not filled in, please setup me up first!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public List<Project> getAll() throws AuthenticationException, IllegalStateException, ConfigurationException {
		List<Project> projects = new ArrayList<Project>();
		int page = 1;
		boolean haveAll = false;
		while (!haveAll) {
			List<Project> temp = getList(100, page);
			projects.addAll(temp);
			// if we have under 100 or over 100 we are finished.
			// if we have over 100 we had to load from disk
			if (temp.size() < 100 || temp.size() > 100) {
				haveAll = true;
			}
			page++;
		}
		return projects;
	}

	private List<Project> getList(int numberOfItems, int page) throws AuthenticationException, IllegalStateException, ConfigurationException {
		List<Project> projects = new ArrayList<Project>();

		ProjectRequest request = new ProjectRequest();
		request.setItemsPerPage(numberOfItems);
		request.setPage(page);
		request.setMethod(RequestMethods.LIST);
		request.setEntity(new Project());
		ProjectResponse response = request.getResponse();
		projects = response.getListResponseEntities().getProjectList();

		return projects;
	}

}
