/**
 * 
 */
package com.monstersoftwarellc.timeease.view;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;

import com.monstersoftwarellc.timeease.TimeEaseSession;
import com.monstersoftwarellc.timeease.dao.IApplicationSettingsDAO;
import com.monstersoftwarellc.timeease.dao.IClientDAO;
import com.monstersoftwarellc.timeease.dao.IProjectDAO;
import com.monstersoftwarellc.timeease.enums.OrderType;
import com.monstersoftwarellc.timeease.model.WhereClause;
import com.monstersoftwarellc.timeease.model.client.Client;
import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.model.impl.ApplicationSettings;
import com.monstersoftwarellc.timeease.model.project.Project;
import com.monstersoftwarellc.timeease.model.task.Task;
import com.monstersoftwarellc.timeease.service.ISecurityService;
import com.monstersoftwarellc.timeease.service.ServiceLocator;
import com.monstersoftwarellc.timeease.widgets.CustomLabelProvider;



/**
 * @author nicholas
 * 
 */
public class ApplicationSettingsView extends ViewPart {

	@SuppressWarnings("unused")
	private DataBindingContext m_bindingContext;

	public static final String ID = "com.monstersoftwarellc.timeease.view.ApplicationSettingsView"; //$NON-NLS-1$

	private ApplicationSettings settings;
	private List<Client> clients;
	private List<Project> projects;
	private List<Task> tasks;

	private IProjectDAO projectDAO = ServiceLocator.locateCurrent(IProjectDAO.class);
	private IApplicationSettingsDAO appSettingsDAO = ServiceLocator.locateCurrent(IApplicationSettingsDAO.class);

	private Composite childComposite;
	private Label paginateSearchPagesLabel;
	private Button paginateSearchPageButton;
	private Spinner numberOfItemsPerPageSpinner;
	private Combo defaultSortOrderCombo;
	private ComboViewer comboViewer;
	private ComboViewer comboViewer_1;
	private ComboViewer comboViewer_2;
	private ComboViewer comboViewer_3;
	private Combo clientCombo;
	private Combo projectCombo;
	private Combo taskCombo;
	

	public ApplicationSettingsView() {
		Account user = ServiceLocator.locateCurrent(ISecurityService.class).getCurrentlyLoggedInUser();
		WhereClause clause = new WhereClause("user", user);
		
		settings = user.getSettings();
		
		// TODO: add active check.
		clients = ServiceLocator.locateCurrent(IClientDAO.class).findAllOrderBy(Collections.singletonList(clause), true, "firstName");

		if(settings.getDefaultClient() != null){
			WhereClause clientClause = new WhereClause("client", settings.getDefaultClient());
			projects = projectDAO.findAllOrderBy(Collections.singletonList(clientClause), true, "name");
		}else{
			projects = Collections.emptyList();
		}
		
		if(settings.getDefaultProject() != null && settings.getDefaultProject().getProjectTasks() != null){
			tasks = settings.getDefaultProject().getProjectTasks();
		}else{
			tasks = Collections.emptyList();
		}
		
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout());
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		childComposite = new Composite(scrolledComposite, SWT.NONE);
		childComposite.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		childComposite.setLayout(new GridLayout(3, false));

		new Label(childComposite, SWT.NONE);

		paginateSearchPagesLabel = new Label(childComposite, SWT.NONE);
		paginateSearchPagesLabel.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		paginateSearchPagesLabel.setText("Paginate Search Pages");
		{
			paginateSearchPageButton = new Button(childComposite, SWT.CHECK);
			paginateSearchPageButton.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		}
		new Label(childComposite, SWT.NONE);
		{
			Label numberOfItemsPerPageLabel = new Label(childComposite, SWT.NONE);
			numberOfItemsPerPageLabel.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
			numberOfItemsPerPageLabel.setText("Number Per Page");
		}
		{
			numberOfItemsPerPageSpinner = new Spinner(childComposite, SWT.BORDER);
			numberOfItemsPerPageSpinner.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
			numberOfItemsPerPageSpinner.setMaximum(500);
		}
		new Label(childComposite, SWT.NONE);
		{
			Label defaultSortOrderLabel = new Label(childComposite, SWT.NONE);
			defaultSortOrderLabel.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
			defaultSortOrderLabel.setText("Default Sort Order");
		}
		{
			comboViewer = new ComboViewer(childComposite, SWT.NONE | SWT.READ_ONLY);
			defaultSortOrderCombo = comboViewer.getCombo();
			defaultSortOrderCombo.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
			defaultSortOrderCombo.setLayoutData(new GridData(SWT.FILL,
					SWT.CENTER, true, false, 1, 1));
		}
		new Label(childComposite, SWT.NONE);
		{
			Label clientComboLabel = new Label(childComposite, SWT.NONE);
			clientComboLabel.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
			clientComboLabel.setText("Default Client");
		}
		{
		comboViewer_1 = new ComboViewer(childComposite, SWT.NONE | SWT.READ_ONLY);
		clientCombo = comboViewer_1.getCombo();
		comboViewer_1.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection transaction = (IStructuredSelection) comboViewer_1.getSelection();
				Client client = (Client) transaction.getFirstElement();
				if(client != null){
					WhereClause clause = new WhereClause("client", client);
					projects = projectDAO.findAllOrderBy(Collections.singletonList(clause), true, "name");
					WritableList writableList = new WritableList(projects, Project.class);
					comboViewer_2.setInput(writableList);
					
					// clear out tasks
					tasks = Collections.emptyList();
					WritableList writableList1 = new WritableList(tasks, Task.class);
					comboViewer_3.setInput(writableList1);
				}
			}
		});
		clientCombo.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		clientCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		}
		new Label(childComposite, SWT.NONE);
		{
			Label projectComboLabel = new Label(childComposite, SWT.NONE);
			projectComboLabel.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
			projectComboLabel.setText("Default Project");
		}
		{
		comboViewer_2 = new ComboViewer(childComposite, SWT.NONE | SWT.READ_ONLY);
		projectCombo = comboViewer_2.getCombo();
		comboViewer_2.addSelectionChangedListener(new ISelectionChangedListener() {
					
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection transaction = (IStructuredSelection) comboViewer_2.getSelection();
				Project project = (Project) transaction.getFirstElement();
				if(project != null){			
					tasks = project.getProjectTasks();
					WritableList writableList = new WritableList(tasks, Task.class);
					comboViewer_3.setInput(writableList);
				}
			}
		});
		projectCombo.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		projectCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		}
		new Label(childComposite, SWT.NONE);
		{
			Label taskComboLabel = new Label(childComposite, SWT.NONE);
			taskComboLabel.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
			taskComboLabel.setText("Default Task");
		}
		{
		comboViewer_3 = new ComboViewer(childComposite, SWT.NONE | SWT.READ_ONLY);
		taskCombo = comboViewer_3.getCombo();
		taskCombo.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		taskCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		}
		new Label(childComposite, SWT.NONE);
		{
			Label keyPreferencesLabel = new Label(childComposite, SWT.NONE);
			keyPreferencesLabel.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
			keyPreferencesLabel.setText("Key Preferences");
		}
		{
			Button hotKeysButton = new Button(childComposite, SWT.NONE
					| SWT.CENTER);
			hotKeysButton.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
			GridData hotKeysLayout = new GridData(SWT.LEFT,
					SWT.CENTER, false, false, 1, 1);
			hotKeysLayout.widthHint = 165;
			hotKeysButton.setLayoutData(hotKeysLayout);
			hotKeysButton.setText("Manage");
			hotKeysButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					IHandlerService handlerService = (IHandlerService) getSite()
							.getService(IHandlerService.class);
					try {
						handlerService.executeCommand("com.monstersoftwarellc.timeease.preferences.keys", null);
					} catch (ExecutionException e) {
						e.printStackTrace();
					} catch (NotDefinedException e) {
						e.printStackTrace();
					} catch (NotEnabledException e) {
						e.printStackTrace();
					} catch (NotHandledException e) {
						e.printStackTrace();
					}
				}
			});
		}

		scrolledComposite.setContent(childComposite);
		scrolledComposite.setMinSize(childComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		m_bindingContext = initDataBindings();
	}

	public class ApplicationSettingsUpdateStrategy extends UpdateValueStrategy {

		/**
		 * 
		 */
		public ApplicationSettingsUpdateStrategy() {
			super(UpdateValueStrategy.POLICY_UPDATE);
		}

		protected IStatus doSet(IObservableValue observableValue, Object value) {
			IStatus ret = super.doSet(observableValue, value);
			if (ret.isOK()) {
				Realm.getDefault().asyncExec(new Runnable() {
					public void run() {
						appSettingsDAO.merge(settings);
						TimeEaseSession.getCurrentInstance().setApplicationSettings(settings);
					}
				});
			}
			return ret;
		}

	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer.setLabelProvider(new CustomLabelProvider());
		comboViewer.setInput(OrderType.values());
		//
		comboViewer_1.setLabelProvider(new CustomLabelProvider());
		comboViewer_1.setContentProvider(new ObservableListContentProvider());
		//
		WritableList writableList_1 = new WritableList(clients, Client.class);
		comboViewer_1.setInput(writableList_1);
		//
		comboViewer_2.setLabelProvider(new CustomLabelProvider());
		comboViewer_2.setContentProvider(new ObservableListContentProvider());
		//
		WritableList writableList_2 = new WritableList(projects, Project.class);
		comboViewer_2.setInput(writableList_2);
		//
		comboViewer_3.setLabelProvider(new CustomLabelProvider());
		comboViewer_3.setContentProvider(new ObservableListContentProvider());
		//
		WritableList writableList_3 = new WritableList(tasks, Task.class);
		comboViewer_3.setInput(writableList_3);
		//
		IObservableValue paginateSearchPageButtonObserveSelectionObserveWidget = SWTObservables.observeDelayedValue(500, SWTObservables.observeSelection(paginateSearchPageButton));
		IObservableValue settingsPaginateSearchPagesObserveValue = BeansObservables.observeValue(settings, "paginateSearchPages");
		bindingContext.bindValue(paginateSearchPageButtonObserveSelectionObserveWidget, settingsPaginateSearchPagesObserveValue, new ApplicationSettingsUpdateStrategy(), null);
		//
		IObservableValue numberOfItemsPerPageSpinnerObserveSelectionObserveWidget = SWTObservables.observeDelayedValue(500, SWTObservables.observeSelection(numberOfItemsPerPageSpinner));
		IObservableValue settingsNumberOfItemsToShowPerPageObserveValue = BeansObservables.observeValue(settings, "numberOfItemsToShowPerPage");
		bindingContext.bindValue(numberOfItemsPerPageSpinnerObserveSelectionObserveWidget, settingsNumberOfItemsToShowPerPageObserveValue, new ApplicationSettingsUpdateStrategy(), null);
		//
		IObservableValue comboViewerObserveSingleSelection = ViewersObservables.observeSingleSelection(comboViewer);
		IObservableValue settingsDefaultSortOrderObserveValue = BeansObservables.observeValue(settings, "defaultSortOrder");
		bindingContext.bindValue(comboViewerObserveSingleSelection, settingsDefaultSortOrderObserveValue, new ApplicationSettingsUpdateStrategy(), null);
		//
		IObservableValue taskObserveSingleSelection = ViewersObservables.observeSingleSelection(comboViewer_1);
		IObservableValue taskObserveDetailValue = BeansObservables.observeValue(settings, "defaultClient");
		bindingContext.bindValue(taskObserveSingleSelection, taskObserveDetailValue,  new ApplicationSettingsUpdateStrategy(), null);
		//
		IObservableValue taskLabelObserveSingleSelection = SWTObservables.observeText(clientCombo);
		IObservableValue taskLabelObserveDetailValue = PojoObservables.observeValue(settings, "defaultClient.label");
		bindingContext.bindValue(taskLabelObserveSingleSelection, taskLabelObserveDetailValue, null, null);
		//
		IObservableValue projectObserveSingleSelection = ViewersObservables.observeSingleSelection(comboViewer_2);
		IObservableValue projectObserveDetailValue = BeansObservables.observeValue(settings, "defaultProject");
		bindingContext.bindValue(projectObserveSingleSelection, projectObserveDetailValue, new ApplicationSettingsUpdateStrategy(), null);
		//
		IObservableValue projectLabelObserveSingleSelection =  SWTObservables.observeText(projectCombo);
		IObservableValue projectLabelObserveDetailValue = PojoObservables.observeValue(settings, "defaultProject.label");
		bindingContext.bindValue(projectLabelObserveSingleSelection, projectLabelObserveDetailValue, null, null);
		//
		IObservableValue clientObserveSingleSelection = ViewersObservables.observeSingleSelection(comboViewer_3);
		IObservableValue tableViewerClientObserveDetailValue = BeansObservables.observeValue(settings, "defaultTask");
		bindingContext.bindValue(clientObserveSingleSelection, tableViewerClientObserveDetailValue, new ApplicationSettingsUpdateStrategy(), null);
		//
		IObservableValue clientLabelObserveSingleSelection =  SWTObservables.observeText(taskCombo);
		IObservableValue clientLabelObserveDetailValue = PojoObservables.observeValue(settings, "defaultTask.label");
		bindingContext.bindValue(clientLabelObserveSingleSelection, clientLabelObserveDetailValue, null, null);
		//
		return bindingContext;
	}
}
