/**
 * 
 */
package com.monstersoftwarellc.timeease.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;

import swing2swt.layout.BorderLayout;

import com.monstersoftwarellc.timeease.dao.ITaskDAO;
import com.monstersoftwarellc.timeease.model.impl.Task;
import com.monstersoftwarellc.timeease.property.IApplicationSettings;
import com.monstersoftwarellc.timeease.property.SettingsFactory;
import com.monstersoftwarellc.timeease.search.TaskSearchCritieria;
import com.monstersoftwarellc.timeease.search.dialogs.TaskSearchCriteriaDialog;
import com.monstersoftwarellc.timeease.service.ISecurityService;
import com.monstersoftwarellc.timeease.service.ServiceLocator;

/**
 * @author nick
 *
 */
public class TasksView extends ViewPart {

	public static final String ID = "com.monstersoftwarellc.timeease.view.TasksView";

	private List<Task> tasks;
	
	private Composite container;
	private Composite taskComposite;
	private Table taskTable;
	private TableViewer tableViewer;
	private Composite childComposite;

	private int page = 0;
	private Button previousPageButton;
	private Button nextPageButton;

	private TaskSearchCritieria searchCriteria = new TaskSearchCritieria();	

	private ISecurityService securityService = ServiceLocator.locateCurrent(ISecurityService.class);
	private SettingsFactory settingsFactory = ServiceLocator.locateCurrent(SettingsFactory.class);
	private ITaskDAO taskDAO = ServiceLocator.locateCurrent(ITaskDAO.class);

	private IApplicationSettings settings = null;
	private Text taskNameText;
	private Text taskDescriptionText;
	private Spinner taskRateSpinner;
	private Button isBillableCheckbox;

	/**
	 * 
	 */
	public TasksView() {
		searchCriteria.setAccount(securityService.getCurrentlyLoggedInUser());
		settings = settingsFactory.getApplicationSettings();
		tasks = new ArrayList<Task>();
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new BorderLayout(0, 0));
		
		taskComposite = new Composite(container, SWT.NONE);
		taskComposite.setLayoutData(BorderLayout.WEST);
		taskComposite.setLayout(new swing2swt.layout.BorderLayout(0, 0));

		tableViewer = new TableViewer(taskComposite, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			/**
			 * Handles user selecting new PosEntry from list of entries.
			 */
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection transaction = (IStructuredSelection) tableViewer.getSelection();
				Task task = (Task) transaction.getFirstElement();
				if (task != null) {
					setControlsToEnabled();
				}
			}
		});
		taskTable = tableViewer.getTable();
		taskTable.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));

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
				TaskSearchCriteriaDialog dialog = new TaskSearchCriteriaDialog(getViewSite().getShell(), searchCriteria);
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
				addNewTask();
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
				deleteTask();
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
		lblNewLabel.setText("Task Name");
		
		taskNameText = new Text(childComposite, SWT.BORDER);
		taskNameText.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		taskNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTaskDescription = new Label(childComposite, SWT.NONE);
		lblTaskDescription.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblTaskDescription.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblTaskDescription.setText("Task Description");
		
		taskDescriptionText = new Text(childComposite, SWT.BORDER);
		taskDescriptionText.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		taskDescriptionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTaskRate = new Label(childComposite, SWT.NONE);
		lblTaskRate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblTaskRate.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblTaskRate.setText("Task Rate");
		
		taskRateSpinner = new Spinner(childComposite, SWT.BORDER);
		taskRateSpinner.setMaximum(100000);
		taskRateSpinner.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		taskRateSpinner.setDigits(2);
		taskRateSpinner.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		
		Label lblIsTaskBillable = new Label(childComposite, SWT.NONE);
		lblIsTaskBillable.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblIsTaskBillable.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblIsTaskBillable.setText("Is Task Billable");
		
		isBillableCheckbox = new Button(childComposite, SWT.CHECK);
		isBillableCheckbox.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		isBillableCheckbox.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));

	
		// set other bindings
		initDataBindings();

		scrolledComposite.setContent(childComposite);
		scrolledComposite.setMinSize(childComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		reloadEntriesBasedOnCriteria();
	}


	/**
	 * @param customer
	 */
	private void addNewTask() {
		Task task = new Task();
		task.setName("Name");
		task.setDescription("Description");
		task.setAccount(securityService.getCurrentlyLoggedInUser());
		taskDAO.persist(task);
		tasks.add(task);
		WritableList writableList = new WritableList(tasks, Task.class);
		tableViewer.setInput(writableList);
		IStructuredSelection sel = new StructuredSelection(task);
		tableViewer.setSelection(null);
		tableViewer.setSelection(sel);
		tableViewer.refresh();
	}
	
	/**
	 * @param customer
	 */
	private void deleteTask() {
		IStructuredSelection transaction = (IStructuredSelection) tableViewer.getSelection();
		Task task = (Task) transaction.getFirstElement();		
		tasks.clear();
		taskDAO.delete(task);
		reloadEntriesBasedOnCriteria();
		tableViewer.setSelection(null);
	}
	
	private void setControlsToEnabled(){
		taskNameText.setEnabled(true);
		taskDescriptionText.setEnabled(true);
		taskRateSpinner.setEnabled(true);
		isBillableCheckbox.setEnabled(true);
	}

	private void setControlsToDisabled() {
		taskNameText.setEnabled(false);
		taskDescriptionText.setEnabled(false);
		taskRateSpinner.setEnabled(false);
		isBillableCheckbox.setEnabled(false);
	}

	/**
	 * 
	 */
	private void reloadEntriesBasedOnCriteria() {
		page = 0;
		tasks = taskDAO.getSearchListForPage(searchCriteria, settings.getDefaultSortOrder(), page, 
				settings.getNumberOfItemsToShowPerPage());
		WritableList writableList = new WritableList(tasks, Task.class);
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
						tasks = taskDAO.getSearchListForPage(searchCriteria, settings.getDefaultSortOrder(), page, 
								settings.getNumberOfItemsToShowPerPage());
						WritableList writableList = new WritableList(tasks, Task.class);
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
						tasks = taskDAO.getSearchListForPage(searchCriteria, settings.getDefaultSortOrder(), page, 
								settings.getNumberOfItemsToShowPerPage());
						WritableList writableList = new WritableList(tasks, Task.class);
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
		long currentPageCount = tasks.size();
		if (currentPageCount < settings.getNumberOfItemsToShowPerPage()
				|| (currentPageCount == settings.getNumberOfItemsToShowPerPage() && taskDAO
				.getRecordCountFromSearchListForPage(searchCriteria, settings.getDefaultSortOrder(), page + 1, 
						settings.getNumberOfItemsToShowPerPage()) == 0)) {
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

	class TaskUpdateStrategy extends UpdateValueStrategy {

		public TaskUpdateStrategy() {
			super(UpdateValueStrategy.POLICY_UPDATE);
		}
		
		public TaskUpdateStrategy(IConverter converter) {
			super(UpdateValueStrategy.POLICY_UPDATE);
			setConverter(converter);
		}

		protected IStatus doSet(IObservableValue observableValue, final Object value) {
			IStatus ret = super.doSet(observableValue, value);
			if (ret.isOK() && value != null && !value.equals("")) {
				Realm.getDefault().asyncExec(new Runnable() {
					public void run() {
						IStructuredSelection transaction = (IStructuredSelection) tableViewer.getSelection();
						Task task = (Task) transaction.getFirstElement();
						if (task != null) {
							task = taskDAO.merge(task);
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
		IObservableMap observeMap = PojoObservables.observeMap(listContentProvider.getKnownElements(), Task.class, "label");
		tableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMap));
		tableViewer.setContentProvider(listContentProvider);
		//
		WritableList writableList = new WritableList(tasks, Task.class);
		tableViewer.setInput(writableList);
		//
		IObservableValue tableViewerObserveSingleSelection = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue tableViewerNameObserveDetailValue = PojoObservables.observeDetailValue(tableViewerObserveSingleSelection, "name", String.class);
		IObservableValue taskNameTextObserveTextObserveWidget = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(taskNameText, SWT.Modify));
		m_bindingContext.bindValue(tableViewerNameObserveDetailValue, taskNameTextObserveTextObserveWidget, null, new TaskUpdateStrategy());
		//
		IObservableValue tableViewerObserveSingleSelection_1 = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue tableViewerDescriptionObserveDetailValue = PojoObservables.observeDetailValue(tableViewerObserveSingleSelection_1, "description", String.class);
		IObservableValue taskDescriptionTextObserveTextObserveWidget = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(taskDescriptionText, SWT.Modify));
		m_bindingContext.bindValue(tableViewerDescriptionObserveDetailValue, taskDescriptionTextObserveTextObserveWidget, null, new TaskUpdateStrategy());
		//
		IObservableValue tableViewerObserveSingleSelection_2 = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue tableViewerRateObserveDetailValue = PojoObservables.observeDetailValue(tableViewerObserveSingleSelection_2, "rate", Double.class);
		IObservableValue taskRateSpinnerObserveSelectionObserveWidget = SWTObservables.observeDelayedValue(500, SWTObservables.observeSelection(taskRateSpinner));
		m_bindingContext.bindValue(tableViewerRateObserveDetailValue, taskRateSpinnerObserveSelectionObserveWidget, null, new TaskUpdateStrategy());
		//
		IObservableValue tableViewerObserveSingleSelection_3 = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue tableViewerBillableObserveDetailValue = PojoObservables.observeDetailValue(tableViewerObserveSingleSelection_3, "billable", boolean.class);
		IObservableValue isBillableCheckboxObserveSelectionObserveWidget = SWTObservables.observeDelayedValue(500, SWTObservables.observeSelection(isBillableCheckbox));
		m_bindingContext.bindValue(tableViewerBillableObserveDetailValue, isBillableCheckboxObserveSelectionObserveWidget, null, new TaskUpdateStrategy());
		//
		return m_bindingContext;
	}
}
