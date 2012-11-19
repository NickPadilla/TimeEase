/**
 * 
 */
package com.monstersoftwarellc.timeease.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.conversion.Converter;
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
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;
import org.springframework.data.domain.Sort;

import swing2swt.layout.BorderLayout;

import com.monstersoftwarellc.timeease.enums.SearchOperators;
import com.monstersoftwarellc.timeease.model.impl.Client;
import com.monstersoftwarellc.timeease.model.impl.Entry;
import com.monstersoftwarellc.timeease.model.impl.Project;
import com.monstersoftwarellc.timeease.model.impl.Task;
import com.monstersoftwarellc.timeease.property.IApplicationSettings;
import com.monstersoftwarellc.timeease.search.EntrySearchCritieria;
import com.monstersoftwarellc.timeease.search.dialogs.EntrySearchCriteriaDialog;
import com.monstersoftwarellc.timeease.service.IClientService;
import com.monstersoftwarellc.timeease.service.IEntryService;
import com.monstersoftwarellc.timeease.service.IProjectService;
import com.monstersoftwarellc.timeease.service.ISecurityService;
import com.monstersoftwarellc.timeease.service.impl.ServiceLocator;
import com.monstersoftwarellc.timeease.service.impl.SettingsService;
import com.monstersoftwarellc.timeease.utility.TimeUtil;

/**
 * @author nick
 *
 */
public class EntriesView extends ViewPart {

	public static final String ID = "com.monstersoftwarellc.timeease.view.entries";

	private List<Entry> entries;
	private List<Client> clients;
	private List<Project> projects;
	private List<Task> tasks;
	
	private Composite container;
	private Composite entryComposite;
	private Table entryTable;
	private TableViewer tableViewer;
	private Composite childComposite;
	private DateTime dateControl;

	private int page = 0;
	private Button previousPageButton;
	private Button nextPageButton;
	private Button btnDelete;

	private EntrySearchCritieria searchCriteria = new EntrySearchCritieria();	

	private ISecurityService securityService = ServiceLocator.locateCurrent(ISecurityService.class);
	private SettingsService settingsService = ServiceLocator.locateCurrent(SettingsService.class);
	private IEntryService entryService = ServiceLocator.locateCurrent(IEntryService.class);
	private IProjectService projectService = ServiceLocator.locateCurrent(IProjectService.class);

	private IApplicationSettings settings = null;
	private Text timer;
	private int counter = 0;
	private boolean timerStarted = false;
	private Button startStopResume;
	private StyledText notes;
	private ComboViewer comboViewer;
	private ComboViewer comboViewer_1;
	private ComboViewer comboViewer_2;
	private Combo clientCombo;
	private Combo projectCombo;
	private Combo taskCombo;

	/**
	 * 
	 */
	public EntriesView() {
		settings = settingsService.getApplicationSettings();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		searchCriteria.setEntryDate(cal.getTime());
		searchCriteria.setEntryDateOperator(SearchOperators.GREATER_THAN_OR_EQUAL);
		entries = new ArrayList<Entry>();
		
		// TODO: add active check.
		clients = ServiceLocator.locateCurrent(IClientService.class).getClientRepository().
				findByAccount(securityService.getCurrentlyLoggedInUser(), new Sort(Sort.Direction.DESC, "firstName"));
		projects = new ArrayList<Project>();
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
		
		entryComposite = new Composite(container, SWT.NONE);
		entryComposite.setLayoutData(BorderLayout.WEST);
		entryComposite.setLayout(new swing2swt.layout.BorderLayout(0, 0));

		tableViewer = new TableViewer(entryComposite, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection transaction = (IStructuredSelection) tableViewer.getSelection();
				Entry entry = (Entry) transaction.getFirstElement();

				if (entry != null) {
					timerStarted = false;
					startStopResume.setText("Start");
					counter = TimeUtil.getSecondsFromDoubleDuration(entry.getHours());
					setControlsToEnabled();
					
					if(entry.getProject() != null && entry.getProject().getClient() != null){
						if(!projects.contains(entry.getProject())){
							projects = projectService.getProjectRepository().findByClient( entry.getProject().getClient(), new Sort(Sort.Direction.ASC, "name"));
							WritableList writableList = new WritableList(projects, Project.class);
							comboViewer_1.setInput(writableList);
						}
					}
				}
			}
		});
		entryTable = tableViewer.getTable();
		entryTable.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));

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
				EntrySearchCriteriaDialog dialog = new EntrySearchCriteriaDialog(getViewSite().getShell(), searchCriteria);
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
				addNewEntry();
				startStopResume.setEnabled(true);
				container.update();
				container.redraw();
				container.layout(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		btnDelete = new Button(controlsComposite, SWT.NONE);
		btnDelete.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		btnDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDelete.setText("Delete");
		btnDelete.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteEntry();
				startStopResume.setEnabled(false);
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
		childComposite.setLayout(new GridLayout(2, true));
		new Label(childComposite, SWT.NONE);
		new Label(childComposite, SWT.NONE);

		Label lblDate = new Label(childComposite, SWT.NONE);
		lblDate.setToolTipText("Hours Format: \\nhh:mm:ss \\nex: 01:00:00 = one hour");
		lblDate.setFont(SWTResourceManager.getFont("Ubuntu", 16, SWT.BOLD));
		lblDate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblDate.setText("Date ");

		dateControl = new DateTime(childComposite, SWT.BORDER | SWT.DROP_DOWN | SWT.LONG);
		dateControl.setFont(SWTResourceManager.getFont("Ubuntu", 16, SWT.NORMAL));
		dateControl.setToolTipText("When Timer is stopped, you may\\noverride the time. No need to \\ninclude seconds.\\nex: 1:30 = one and a half hours");
		dateControl.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));

		timer = new Text(childComposite, SWT.BORDER | SWT.CENTER);
		timer.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.BOLD));
		timer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		timer.setTextLimit(8);
		timer.setToolTipText("When Timer is stopped, you may\noverride the time. No need to \ninclude seconds.\nex: 1:30 = one and a half hours");

		new Label(childComposite, SWT.NONE);
		new Label(childComposite, SWT.NONE);

		Label lblNewLabel = new Label(childComposite, SWT.NONE);
		lblNewLabel.setToolTipText("");
		lblNewLabel.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		lblNewLabel.setText("Please Select A Client");

		comboViewer = new ComboViewer(childComposite, SWT.NONE | SWT.READ_ONLY);
		clientCombo = comboViewer.getCombo();
		comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection transaction = (IStructuredSelection) comboViewer.getSelection();
				Client client = (Client) transaction.getFirstElement();
				if(client != null){	
					// clear out tasks
					tasks = Collections.emptyList();
					WritableList writableList1 = new WritableList(tasks, Task.class);
					comboViewer_2.setInput(writableList1);
										
					projects = projectService.getProjectRepository().findByClient(client, new Sort(Sort.Direction.ASC, "name"));
					WritableList writableList = new WritableList(projects, Project.class);
					comboViewer_1.setInput(writableList);
				}
			}
		});
		clientCombo.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		clientCombo.setToolTipText("You can set defaults by selecting Time Ease --> Application Settings.");
		clientCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		new Label(childComposite, SWT.NONE);
		new Label(childComposite, SWT.NONE);

		Label lblNewLabel_1 = new Label(childComposite, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblNewLabel_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		lblNewLabel_1.setText("Please Select A Project");

		comboViewer_1 = new ComboViewer(childComposite, SWT.NONE | SWT.READ_ONLY);
		projectCombo = comboViewer_1.getCombo();
		comboViewer_1.addSelectionChangedListener(new ISelectionChangedListener() {
					
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection transaction = (IStructuredSelection) comboViewer_1.getSelection();
				Project project = (Project) transaction.getFirstElement();
				if(project != null){
					WritableList writableList = new WritableList(project.getProjectTasks(), Task.class);
					comboViewer_2.setInput(writableList);
					
					IStructuredSelection trans = (IStructuredSelection) tableViewer.getSelection();
					Entry entry = (Entry) trans.getFirstElement();
					
					if(entry != null && entry.getTask() != null){
						IStructuredSelection sel = new StructuredSelection(entry.getTask());
						comboViewer_2.setSelection(sel);
					}
				}
			}
		});
		projectCombo.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		projectCombo.setToolTipText("You can set defaults by selecting Time Ease --> Application Settings.");
		projectCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		new Label(childComposite, SWT.NONE);
		new Label(childComposite, SWT.NONE);

		Label lblNewLabel_2 = new Label(childComposite, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblNewLabel_2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		lblNewLabel_2.setText("Please Select A Task");

		comboViewer_2 = new ComboViewer(childComposite, SWT.NONE | SWT.READ_ONLY);
		taskCombo = comboViewer_2.getCombo();
		taskCombo.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		taskCombo.setToolTipText("You can set defaults by selecting Time Ease --> Application Settings.");
		taskCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		new Label(childComposite, SWT.NONE);
		new Label(childComposite, SWT.NONE);

		Label lblNewLabel_3 = new Label(childComposite, SWT.NONE);
		lblNewLabel_3.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblNewLabel_3.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		lblNewLabel_3.setText("Notes");

		notes = new StyledText(childComposite, SWT.BORDER);
		notes.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		notes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));

		startStopResume = new Button(childComposite, SWT.NONE);
		startStopResume.setFont(SWTResourceManager.getFont("Ubuntu", 16, SWT.BOLD));
		startStopResume.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		startStopResume.setText("Start");
		startStopResume.setEnabled(false);
		startStopResume.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// not usable during this operation, or else you can end up double 
				// clicking and causing funkiness.
				startStopResume.setEnabled(false);
				timerStarted = !timerStarted;
				if(timerStarted){
					// set the counter if needed, checks the two strings for equality
					setCounterFromCounterStringAndTimerText();
					// set timer to a read only
					timer.setEnabled(false);
					manageTimer(childComposite);
				}else{
					timer.setEnabled(true);
				}
				// we run this using an async call so that we can 
				// solve some problems with running multilple timers
				// this can happen with double clicks
				Display.getDefault().timerExec(1000, new Runnable() {

					@Override
					public void run() {	
						// re-enable now so user can stop timer
						startStopResume.setEnabled(true);
					}
				}); 
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});		

		// set other bindings
		initDataBindings();

		scrolledComposite.setContent(childComposite);
		scrolledComposite.setMinSize(childComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				
		//setControlsToDisabled();

		reloadEntriesBasedOnCriteria();
	}

	protected void manageTimer(final Composite parent) {
		// here is the timer				
		Display.getDefault().asyncExec(new Runnable() { 
			public void run() {  
				if(timerStarted){
					if (parent.isDisposed()) return;

					counter++;

					String time = TimeUtil.getTimerStringFromCounter(counter);

					timer.setText(time); 
					startStopResume.setText("Stop");
					//System.out.println(time);
					parent.getDisplay().timerExec (1000, this);					
				}else{
					tableViewer.refresh();
					startStopResume.setText("Start");
				}
			}  
		}); 
	}

	/**
	 * This method will reset the counter based on the timer text. This is because the user
	 * may have manually updated the time.  
	 * @param currentCounterString
	 * @param newTimerString
	 * @throws NumberFormatException
	 */
	private void setCounterFromCounterStringAndTimerText() throws NumberFormatException {
		// we have started the timer, see what our time is set to
		// change of needed
		String currentCounterString = TimeUtil.getTimerStringFromCounter(counter);
		String newTimerString = timer.getText().trim();
		if(!currentCounterString.equals(newTimerString) && validateTimerString(newTimerString)){			
			counter = getCounterFromStringFormat(newTimerString);
		}
	}

	/**
	 * @param newTimerString
	 * @return
	 * @throws NumberFormatException
	 */
	private int getCounterFromStringFormat(String newTimerString)
			throws NumberFormatException {
		// new or updated time, need to update counter
		String[] hoursMinutesSeconds = newTimerString.split(":");
		int countInSeconds = 0;
		int index = 1;
		for(String string : hoursMinutesSeconds){
			// get right calculations
			if(index == 1){
				// hours
				countInSeconds += (Integer.parseInt(string)*60)*60;
			}else if(index == 2){
				// minutes
				countInSeconds += Integer.parseInt(string)*60;
			}else{
				// seconds
				countInSeconds += Integer.parseInt(string);
			}
			index++;
		}
		return countInSeconds;
	}

	private boolean validateTimerString(String text) {
		boolean ret = false;
		if(text != null && !text.isEmpty()){
			if(text.matches("\\d\\d")){
				ret = true;
			} else if(text.matches("\\d\\d:\\d\\d")){
				ret = true;
			} else if(text.matches("\\d\\d:\\d\\d:\\d\\d")){
				ret = true;
			}
		}
		return ret;
	}

	/**
	 * @param customer
	 */
	private void addNewEntry() {
		Entry entry = new Entry();
		entry.setEntryDate(new Date());
		entry.setHours(0d);
		if(settings.getDefaultProject() != null){
			entry.setProject(settings.getDefaultProject());
		}
		if(settings.getDefaultTask() != null){
			entry.setTask(settings.getDefaultTask());
		}
		entry.setAccount(securityService.getCurrentlyLoggedInUser());
		entryService.getEntryRepository().saveAndFlush(entry);
		entries.add(entry);
		counter = 0;
		WritableList writableList = new WritableList(entries, Entry.class);
		tableViewer.setInput(writableList);
		IStructuredSelection sel = new StructuredSelection(entry);
		tableViewer.setSelection(null);
		tableViewer.setSelection(sel);
		tableViewer.refresh();
	}
	
	/**
	 * @param customer
	 */
	private void deleteEntry() {
		IStructuredSelection transaction = (IStructuredSelection) tableViewer.getSelection();
		Entry entry = (Entry) transaction.getFirstElement();		
		entries.clear();
		entryService.getEntryRepository().delete(entry);
		reloadEntriesBasedOnCriteria();
	}
	
	private void setControlsToEnabled(){
		timer.setEnabled(true);
		dateControl.setEnabled(true);
		startStopResume.setEnabled(true);
		notes.setEnabled(true);
		comboViewer.getCombo().setEnabled(true);
		comboViewer_1.getCombo().setEnabled(true);
		comboViewer_2.getCombo().setEnabled(true);
		btnDelete.setEnabled(true);
	}

	private void setControlsToDisabled() {
		timer.setEnabled(false);
		dateControl.setEnabled(false);
		startStopResume.setEnabled(false);
		notes.setEnabled(false);
		comboViewer.getCombo().setEnabled(false);
		comboViewer_1.getCombo().setEnabled(false);
		comboViewer_2.getCombo().setEnabled(false);
		btnDelete.setEnabled(false);
	}

	/**
	 * 
	 */
	private void reloadEntriesBasedOnCriteria() {
		page = 0;
		entries = entryService.getEntryRepository().getSearchListForPage(searchCriteria, settings.getDefaultSortOrder(), page, 
				settings.getNumberOfItemsToShowPerPage());
		WritableList writableList = new WritableList(entries, Entry.class);
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
						entries = entryService.getEntryRepository().getSearchListForPage(searchCriteria, settings.getDefaultSortOrder(), page, 
								settings.getNumberOfItemsToShowPerPage());
						WritableList writableList = new WritableList(entries, Entry.class);
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
						entries = entryService.getEntryRepository().getSearchListForPage(searchCriteria, settings.getDefaultSortOrder(), page, 
								settings.getNumberOfItemsToShowPerPage());
						WritableList writableList = new WritableList(entries, Entry.class);
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
		long currentPageCount = entries.size();
		if (currentPageCount < settings.getNumberOfItemsToShowPerPage()
				|| (currentPageCount == settings.getNumberOfItemsToShowPerPage() && entryService.getEntryRepository()
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

	class EntryUpdateStrategy extends UpdateValueStrategy {

		public EntryUpdateStrategy() {
			super(UpdateValueStrategy.POLICY_UPDATE);
		}
		
		public EntryUpdateStrategy(IConverter converter) {
			super(UpdateValueStrategy.POLICY_UPDATE);
			setConverter(converter);
		}

		protected IStatus doSet(IObservableValue observableValue, final Object value) {
			IStatus ret = super.doSet(observableValue, value);
			if (ret.isOK() && value != null && !value.equals("")) {
				Realm.getDefault().asyncExec(new Runnable() {
					public void run() {
						IStructuredSelection transaction = (IStructuredSelection) tableViewer.getSelection();
						Entry entry = (Entry) transaction.getFirstElement();
						if (entry != null) {
							entry = entryService.getEntryRepository().saveAndFlush(entry);
							tableViewer.refresh();
						}
					}
				});
			}
			return ret;
		}
	}
	class NoSaveUpdateStrategy extends UpdateValueStrategy {

		public NoSaveUpdateStrategy() {
			super(UpdateValueStrategy.POLICY_UPDATE);
		}
		
		public NoSaveUpdateStrategy(IConverter converter) {
			super(UpdateValueStrategy.POLICY_UPDATE);
			setConverter(converter);
		}

		protected IStatus doSet(IObservableValue observableValue, final Object value) {			
			return super.doSet(observableValue, value);
		}
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext m_bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap observeMap = BeansObservables.observeMap(listContentProvider.getKnownElements(), Entry.class, "label");
		tableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMap));
		tableViewer.setContentProvider(listContentProvider);
		//
		WritableList writableList = new WritableList(entries, Entry.class);
		tableViewer.setInput(writableList);
		//
		ObservableListContentProvider listContentProvider_1 = new ObservableListContentProvider();
		IObservableMap observeMap_1 = BeansObservables.observeMap(listContentProvider_1.getKnownElements(), Client.class, "label");
		comboViewer.setLabelProvider(new ObservableMapLabelProvider(observeMap_1));
		comboViewer.setContentProvider(listContentProvider_1);
		//
		WritableList writableList_1 = new WritableList(clients, Client.class);
		comboViewer.setInput(writableList_1);
		//
		ObservableListContentProvider listContentProvider_2 = new ObservableListContentProvider();
		IObservableMap observeMap_2 = BeansObservables.observeMap(listContentProvider_2.getKnownElements(), Project.class, "label");
		comboViewer_1.setLabelProvider(new ObservableMapLabelProvider(observeMap_2));
		comboViewer_1.setContentProvider(listContentProvider_2);
		//
		WritableList writableList_2 = new WritableList(projects, Project.class);
		comboViewer_1.setInput(writableList_2);
		//
		ObservableListContentProvider listContentProvider_3 = new ObservableListContentProvider();
		IObservableMap observeMap_3 = BeansObservables.observeMap(listContentProvider_3.getKnownElements(), Task.class, "label");
		comboViewer_2.setLabelProvider(new ObservableMapLabelProvider(observeMap_3));
		comboViewer_2.setContentProvider(listContentProvider_3);
		//
		WritableList writableList_3 = new WritableList(tasks, Task.class);
		comboViewer_2.setInput(writableList_3);
		//
		IObservableValue tableViewerObserveSingleSelection = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue tableViewerHoursObserveDetailValue = BeansObservables.observeDetailValue(tableViewerObserveSingleSelection, Entry.class, "hours", Double.class);
		IObservableValue goldrushHoursTextObserveTextObserveWidget = SWTObservables.observeText(timer, SWT.Modify);
		m_bindingContext.bindValue(tableViewerHoursObserveDetailValue, goldrushHoursTextObserveTextObserveWidget, new NoSaveUpdateStrategy(new Converter(null,null) {
			@Override
			public Object convert(Object fromObject) {
				String ret = "00:00:00";
				if(fromObject != null){
					ret = TimeUtil.getFormatedDurationForDouble((Double)fromObject);
				}
				return ret;
			}
		}), new EntryUpdateStrategy(new Converter(null,null) {
			@Override
			public Object convert(Object fromObject) {
				double ret = 0d;
				if(fromObject != null){
					ret = TimeUtil.getDuration(getCounterFromStringFormat((String)fromObject));
				}
				return ret;
			}
		}));
		//
		IObservableValue tableViewerObserveSingleSelection_1 = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue taskObserveDetailValue = PojoObservables.observeDetailValue(tableViewerObserveSingleSelection_1, "task", Task.class);
		IObservableValue taskObserveSingleSelection = ViewersObservables.observeSingleSelection(comboViewer_2);
		m_bindingContext.bindValue(taskObserveDetailValue, taskObserveSingleSelection, null, new EntryUpdateStrategy());
		//
		IObservableValue tableViewerObserveSingleSelection_2 = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue tableViewerTaskObserveDetailValue = BeansObservables.observeDetailValue(tableViewerObserveSingleSelection_2, Entry.class, "task.label", String.class);
		IObservableValue taskComboObserveSelectionObserveWidget = SWTObservables.observeSelection(taskCombo);
		m_bindingContext.bindValue(tableViewerTaskObserveDetailValue, taskComboObserveSelectionObserveWidget, new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE), new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER));
		//
		IObservableValue tableViewerObserveSingleSelection_3 = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue projectObserveDetailValue = PojoObservables.observeDetailValue(tableViewerObserveSingleSelection_3, "project", Project.class);
		IObservableValue projectObserveSingleSelection = ViewersObservables.observeSingleSelection(comboViewer_1);
		m_bindingContext.bindValue(projectObserveDetailValue, projectObserveSingleSelection, null, new EntryUpdateStrategy());
		//
		IObservableValue tableViewerObserveSingleSelection_4 = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue tableViewerProjectObserveDetailValue = BeansObservables.observeDetailValue(tableViewerObserveSingleSelection_4, Entry.class, "project.label", String.class);
		IObservableValue projectComboObserveSelectionObserveWidget = SWTObservables.observeSelection(projectCombo);
		m_bindingContext.bindValue(tableViewerProjectObserveDetailValue, projectComboObserveSelectionObserveWidget, new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE), new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER));
		//
		IObservableValue tableViewerObserveSingleSelection_5 = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue tableViewerNotesObserveDetailValue = BeansObservables.observeDetailValue(tableViewerObserveSingleSelection_5, Entry.class, "notes", String.class);
		IObservableValue notesObserveTextObserveWidget = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(notes, SWT.Modify));
		m_bindingContext.bindValue(tableViewerNotesObserveDetailValue, notesObserveTextObserveWidget, null, new EntryUpdateStrategy());
		//
		IObservableValue tableViewerObserveSingleSelection_6 = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue tableViewerEntryDateObserveDetailValue = BeansObservables.observeDetailValue(tableViewerObserveSingleSelection_6, Entry.class, "entryDate", Date.class);
		IObservableValue dateControlObserveSelectionObserveWidget = SWTObservables.observeDelayedValue(500, SWTObservables.observeSelection(dateControl));
		m_bindingContext.bindValue(tableViewerEntryDateObserveDetailValue, dateControlObserveSelectionObserveWidget, null, new EntryUpdateStrategy());
		//
		IObservableValue tableViewerObserveSingleSelection_7 = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue tableViewerClientObserveDetailValue = BeansObservables.observeDetailValue(tableViewerObserveSingleSelection_7, Entry.class, "project.client.label", String.class);
		IObservableValue clientComboObserveSelectionObserveWidget = SWTObservables.observeSelection(clientCombo);
		m_bindingContext.bindValue(tableViewerClientObserveDetailValue, clientComboObserveSelectionObserveWidget, new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE), new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER));
		//
		return m_bindingContext;
	}
}
