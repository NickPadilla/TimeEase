/**
 * 
 */
package com.monstersoftwarellc.timeease.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;
import org.springframework.data.domain.Sort;

import swing2swt.layout.BorderLayout;

import com.monstersoftwarellc.timeease.model.impl.Client;
import com.monstersoftwarellc.timeease.model.impl.Project;
import com.monstersoftwarellc.timeease.model.impl.Task;
import com.monstersoftwarellc.timeease.property.IApplicationSettings;
import com.monstersoftwarellc.timeease.search.ProjectSearchCritieria;
import com.monstersoftwarellc.timeease.search.dialogs.ProjectSearchCriteriaDialog;
import com.monstersoftwarellc.timeease.service.IClientService;
import com.monstersoftwarellc.timeease.service.IProjectService;
import com.monstersoftwarellc.timeease.service.ISecurityService;
import com.monstersoftwarellc.timeease.service.ITaskService;
import com.monstersoftwarellc.timeease.service.impl.ServiceLocator;
import com.monstersoftwarellc.timeease.service.impl.SettingsService;
import com.monstersoftwarellc.timeease.widgets.CustomLabelProvider;

/**
 * @author nick
 *
 */
public class ProjectsView extends ViewPart {

	public static final String ID = "com.monstersoftwarellc.timeease.view.ProjectsView";

	private List<Project> projects;
	private List<Client> clients;
	private List<Task> tasks;
	
	private Composite container;
	private Composite projectComposite;
	private Table projectTable;
	private TableViewer tableViewer;
	private Composite childComposite;

	private int page = 0;
	private Button previousPageButton;
	private Button nextPageButton;

	private ProjectSearchCritieria searchCriteria = new ProjectSearchCritieria();	

	private ISecurityService securityService = ServiceLocator.locateCurrent(ISecurityService.class);
	private SettingsService settingsService = ServiceLocator.locateCurrent(SettingsService.class);
	private IProjectService projectService = ServiceLocator.locateCurrent(IProjectService.class);

	private IApplicationSettings settings = null;
	private Text projectNameText;
	private Text projectDescriptionText;
	private Spinner projectRateSpinner;
	private ListViewer listViewer;
	private ComboViewer comboViewer;
	private Combo clientCombo;

	/**
	 * 
	 */
	public ProjectsView() {
		searchCriteria.setAccount(securityService.getCurrentlyLoggedInUser());
		settings = settingsService.getApplicationSettings();
		projects = new ArrayList<Project>();
		clients = ServiceLocator.locateCurrent(IClientService.class).getClientRepository().findAll(new Sort(Sort.Direction.ASC, "firstName"));
		tasks = ServiceLocator.locateCurrent(ITaskService.class).getTaskRepository().findAll(new Sort(Sort.Direction.ASC, "name"));
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new BorderLayout(0, 0));
		
		projectComposite = new Composite(container, SWT.NONE);
		projectComposite.setLayoutData(BorderLayout.WEST);
		projectComposite.setLayout(new swing2swt.layout.BorderLayout(0, 0));

		tableViewer = new TableViewer(projectComposite, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			/**
			 * Handles user selecting new PosEntry from list of entries.
			 */
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection transaction = (IStructuredSelection) tableViewer.getSelection();
				Project project = (Project) transaction.getFirstElement();
				if (project != null) {
					setControlsToEnabled();
				}
			}
		});
		projectTable = tableViewer.getTable();
		projectTable.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));

		final Composite controlsComposite = new Composite(container, SWT.NONE);
		controlsComposite.setLayoutData(BorderLayout.NORTH);
		controlsComposite.setLayout(new GridLayout(6, true));

		addPagination(controlsComposite);

		Button searchParameters = new Button(controlsComposite, SWT.NONE);
		searchParameters.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		searchParameters.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		searchParameters.setText("Search");
		searchParameters.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ProjectSearchCriteriaDialog dialog = new ProjectSearchCriteriaDialog(getViewSite().getShell(), searchCriteria);
				dialog.open();
				searchCriteria = dialog.getCriteria();
				reloadEntriesBasedOnCriteria();
				container.update();
				container.redraw();
				container.layout(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		Button addEntryButton = new Button(controlsComposite, SWT.NONE);
		addEntryButton.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		addEntryButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addEntryButton.setText("New");
		addEntryButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				addNewProject();
				container.update();
				container.redraw();
				container.layout(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		Button btnDelete = new Button(controlsComposite, SWT.NONE);
		btnDelete.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		btnDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDelete.setText("Delete");
		new Label(controlsComposite, SWT.NONE);
		new Label(controlsComposite, SWT.NONE);
		new Label(controlsComposite, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteProject();
				container.update();
				container.redraw();
				container.layout(true);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});


		ScrolledComposite scrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(BorderLayout.CENTER);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		childComposite = new Composite(scrolledComposite, SWT.NONE);
		childComposite.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		childComposite.setLayout(new GridLayout(2, true));
		new Label(childComposite, SWT.NONE);
		new Label(childComposite, SWT.NONE);
		new Label(childComposite, SWT.NONE);
		new Label(childComposite, SWT.NONE);
		
		Label lblNewLabel = new Label(childComposite, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblNewLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Project Name");
		
		projectNameText = new Text(childComposite, SWT.BORDER);
		projectNameText.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		projectNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTaskDescription = new Label(childComposite, SWT.NONE);
		lblTaskDescription.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblTaskDescription.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblTaskDescription.setText("Project Description");
		
		projectDescriptionText = new Text(childComposite, SWT.BORDER);
		projectDescriptionText.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		projectDescriptionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTaskRate = new Label(childComposite, SWT.NONE);
		lblTaskRate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblTaskRate.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblTaskRate.setText("Project Rate");
		
		projectRateSpinner = new Spinner(childComposite, SWT.BORDER);
		projectRateSpinner.setMaximum(100000);
		projectRateSpinner.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		projectRateSpinner.setDigits(2);
		projectRateSpinner.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		
		Label lblClient = new Label(childComposite, SWT.NONE);
		lblClient.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblClient.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblClient.setText("Client");
		
		comboViewer = new ComboViewer(childComposite, SWT.NONE | SWT.READ_ONLY);
		clientCombo = comboViewer.getCombo();
		clientCombo.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		clientCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTasks = new Label(childComposite, SWT.NONE);
		lblTasks.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblTasks.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblTasks.setText("Tasks");
		
		listViewer = new ListViewer(childComposite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		org.eclipse.swt.widgets.List taskList = listViewer.getList();
		taskList.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		taskList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		// set other bindings
		initDataBindings();

		scrolledComposite.setContent(childComposite);
		scrolledComposite.setMinSize(childComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				
		reloadEntriesBasedOnCriteria();
	}


	/**
	 * @param customer
	 */
	private void addNewProject() {
		Project project = new Project();
		project.setName("Name");
		project.setDescription("Description");
		project.setAccount(securityService.getCurrentlyLoggedInUser());
		projectService.getProjectRepository().saveAndFlush(project);
		projects.add(project);
		WritableList writableList = new WritableList(projects, Project.class);
		tableViewer.setInput(writableList);
		IStructuredSelection sel = new StructuredSelection(project);
		tableViewer.setSelection(null);
		tableViewer.setSelection(sel);
		tableViewer.refresh();
	}
	
	/**
	 * @param customer
	 */
	private void deleteProject() {
		IStructuredSelection transaction = (IStructuredSelection) tableViewer.getSelection();
		Project project = (Project) transaction.getFirstElement();		
		projects.clear();
		projectService.getProjectRepository().delete(project);
		reloadEntriesBasedOnCriteria();
		tableViewer.setSelection(null);
	}
	
	private void setControlsToEnabled(){
		projectNameText.setEnabled(true);
		projectDescriptionText.setEnabled(true);
		projectRateSpinner.setEnabled(true);
	}

	private void setControlsToDisabled() {
		projectNameText.setEnabled(false);
		projectDescriptionText.setEnabled(false);
		projectRateSpinner.setEnabled(false);
	}

	/**
	 * 
	 */
	private void reloadEntriesBasedOnCriteria() {
		page = 0;
		projects = projectService.getProjectRepository().getSearchListPage(searchCriteria, page, settings).getContent();
		WritableList writableList = new WritableList(projects, Project.class);
		tableViewer.setInput(writableList);
		if (previousPageButton != null) {
			setNextEnabledState();
			previousPageButton.setEnabled(false);
		}
		tableViewer.refresh();
		setControlsToDisabled();
	}

	/**
	 * 
	 */
	private void addPagination(Composite controlsComposite) {

		if (settings.isPaginateSearchPages()
				&& settings.getNumberOfItemsToShowPerPage() > 0) {

			if (previousPageButton == null) {
				previousPageButton = new Button(controlsComposite, SWT.NONE);
				previousPageButton.setText("Previous");
				previousPageButton.setEnabled(false);
				previousPageButton.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
				previousPageButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
				previousPageButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						--page;
						if (page == 0) {
							previousPageButton.setEnabled(false);
						}
						projects = projectService.getProjectRepository().getSearchListPage(searchCriteria, page, settings).getContent();
						WritableList writableList = new WritableList(projects, Project.class);
						tableViewer.setInput(writableList);
						nextPageButton.setEnabled(true);
						tableViewer.refresh();
					}
				});

				nextPageButton = new Button(controlsComposite, SWT.NONE);
				nextPageButton.setText("Next");
				nextPageButton.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
				nextPageButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
				nextPageButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						++page;
						projects = projectService.getProjectRepository().getSearchListPage(searchCriteria, page, settings).getContent();
						WritableList writableList = new WritableList(projects, Project.class);
						tableViewer.setInput(writableList);
						setNextEnabledState();
						previousPageButton.setEnabled(true);
						tableViewer.refresh();
					}
				});
				controlsComposite.layout();
			}
		}
	}

	/**
	 * 
	 */
	private void setNextEnabledState() {
		// ensure we disable this control if we reach the end of
		// entries
		long currentPageCount = projects.size();
		if (currentPageCount < settings.getNumberOfItemsToShowPerPage()
				|| (currentPageCount == settings.getNumberOfItemsToShowPerPage() 
					&& projectService.getProjectRepository().getSearchListPageCount(searchCriteria, page, settings) == 0)) {
			nextPageButton.setEnabled(false);
		} else {
			nextPageButton.setEnabled(true);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {

	}

	class ProjectUpdateStrategy extends UpdateValueStrategy {

		public ProjectUpdateStrategy() {
			super(UpdateValueStrategy.POLICY_UPDATE);
		}
		
		public ProjectUpdateStrategy(IConverter converter) {
			super(UpdateValueStrategy.POLICY_UPDATE);
			setConverter(converter);
		}

		protected IStatus doSet(IObservableValue observableValue, final Object value) {
			IStatus ret = super.doSet(observableValue, value);
			if (ret.isOK() && value != null && !value.equals("")) {
				Realm.getDefault().asyncExec(new Runnable() {
					public void run() {
						IStructuredSelection transaction = (IStructuredSelection) tableViewer.getSelection();
						Project project = (Project) transaction.getFirstElement();
						if (project != null) {
							project = projectService.getProjectRepository().saveAndFlush(project);
							tableViewer.refresh();
						}
					}
				});
			}
			return ret;
		}
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext m_bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap observeMap = BeansObservables.observeMap(listContentProvider.getKnownElements(), Project.class, "label");
		tableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMap));
		tableViewer.setContentProvider(listContentProvider);
		//
		WritableList writableList = new WritableList(projects, Project.class);
		tableViewer.setInput(writableList);
		//
		IObservableValue tableViewerObserveSingleSelection = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue tableViewerNameObserveDetailValue = PojoObservables.observeDetailValue(tableViewerObserveSingleSelection, "name", String.class);
		IObservableValue taskNameTextObserveTextObserveWidget = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(projectNameText, SWT.Modify));
		m_bindingContext.bindValue(tableViewerNameObserveDetailValue, taskNameTextObserveTextObserveWidget, null, new ProjectUpdateStrategy());
		//
		IObservableValue tableViewerObserveSingleSelection_1 = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue tableViewerDescriptionObserveDetailValue = PojoObservables.observeDetailValue(tableViewerObserveSingleSelection_1, "description", String.class);
		IObservableValue taskDescriptionTextObserveTextObserveWidget = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(projectDescriptionText, SWT.Modify));
		m_bindingContext.bindValue(tableViewerDescriptionObserveDetailValue, taskDescriptionTextObserveTextObserveWidget, null, new ProjectUpdateStrategy());
		//
		IObservableValue tableViewerObserveSingleSelection_2 = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue tableViewerRateObserveDetailValue = PojoObservables.observeDetailValue(tableViewerObserveSingleSelection_2, "rate", Double.class);
		IObservableValue taskRateSpinnerObserveSelectionObserveWidget = SWTObservables.observeDelayedValue(500, SWTObservables.observeSelection(projectRateSpinner));
		m_bindingContext.bindValue(tableViewerRateObserveDetailValue, taskRateSpinnerObserveSelectionObserveWidget, null, new ProjectUpdateStrategy());
		//
		listViewer.setContentProvider(new ObservableListContentProvider());
		listViewer.setLabelProvider(new CustomLabelProvider());
		//
		WritableList writableList2 = new WritableList(tasks, Task.class);
		listViewer.setInput(writableList2);
		//
		IObservableValue tableViewerObserveSingleSelection_3 = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableList tableViewerProjectTasksObserveDetailList = PojoObservables.observeDetailList(tableViewerObserveSingleSelection_3, "projectTasks", Task.class);
		IObservableList listViewerObserveMultiSelection = ViewersObservables.observeMultiSelection(listViewer);
		m_bindingContext.bindList(tableViewerProjectTasksObserveDetailList, listViewerObserveMultiSelection, null, new ListUpdateStrategy());
		listViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sel = (IStructuredSelection) tableViewer.getSelection();
				Project project = (Project) sel.getFirstElement();
				List<String> items = new ArrayList<String>();
				if(project != null){
					for(Task task : project.getProjectTasks()){
						items.add(task.getLabel());
					}
				}			
				listViewer.getList().setSelection(items.toArray(new String[0]));
				listViewer.refresh();
			}
		});
		//
		comboViewer.setContentProvider(new ObservableListContentProvider());
		comboViewer.setLabelProvider(new CustomLabelProvider());
		//
		WritableList writableList1 = new WritableList(clients, Client.class);
		comboViewer.setInput(writableList1);
		//
		IObservableValue tableViewerObserveSingleSelection_4 = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue tableViewerStatusObserveDetailValue = PojoObservables.observeDetailValue(tableViewerObserveSingleSelection_4, "client", Client.class);
		IObservableValue comboViewerObserveSingleSelection = ViewersObservables.observeSingleSelection(comboViewer);
		m_bindingContext.bindValue(tableViewerStatusObserveDetailValue, comboViewerObserveSingleSelection, null, new ProjectUpdateStrategy());	
		//
		IObservableValue tableViewerObserveSingleSelection_5 = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue tableViewerClientObserveDetailValue = BeansObservables.observeDetailValue(tableViewerObserveSingleSelection_5, Project.class, "client.label", String.class);
		IObservableValue clientComboObserveSelectionObserveWidget = SWTObservables.observeSelection(clientCombo);
		m_bindingContext.bindValue(tableViewerClientObserveDetailValue, clientComboObserveSelectionObserveWidget, new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE), new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER));
		//
		return m_bindingContext;
	}	
	
	class ListUpdateStrategy extends UpdateListStrategy {

		public ListUpdateStrategy() {
			super(UpdateValueStrategy.POLICY_UPDATE);
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.core.databinding.UpdateListStrategy#doAdd(org.eclipse.core.databinding.observable.list.IObservableList, java.lang.Object, int)
		 */
		@Override
		protected IStatus doAdd(IObservableList observableList, final Object element, int index) {
			IStatus ret = super.doAdd(observableList, element, index);
			if (ret.isOK()) {
				Realm.getDefault().asyncExec(new Runnable() {
					public void run() {
						IStructuredSelection transaction = (IStructuredSelection) tableViewer.getSelection();
						Project project = (Project) transaction.getFirstElement();
						if(project != null){
							project = projectService.getProjectRepository().saveAndFlush(project);
							tableViewer.refresh();
						}
					}
				});
			}
			return ret;
		}
	}
}
