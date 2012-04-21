/**
 * 
 */
package com.monstersoftwarellc.timeease.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;

import swing2swt.layout.BorderLayout;

import com.monstersoftwarellc.timeease.dao.IClientDAO;
import com.monstersoftwarellc.timeease.model.client.Client;
import com.monstersoftwarellc.timeease.model.impl.ApplicationSettings;
import com.monstersoftwarellc.timeease.search.ClientSearchCritieria;
import com.monstersoftwarellc.timeease.search.dialogs.ClientSearchCriteriaDialog;
import com.monstersoftwarellc.timeease.service.ISecurityService;
import com.monstersoftwarellc.timeease.service.ServiceLocator;

/**
 * @author nick
 *
 */
public class ClientsView extends ViewPart {

	public static final String ID = "com.monstersoftwarellc.timeease.view.ClientsView";

	private List<Client> clients;
	
	private Composite container;
	private Composite clientComposite;
	private Table clientTable;
	private TableViewer tableViewer;
	private Composite childComposite;

	private int page = 0;
	private Button previousPageButton;
	private Button nextPageButton;

	private ClientSearchCritieria searchCriteria = new ClientSearchCritieria();	

	private ISecurityService securityService = ServiceLocator.locateCurrent(ISecurityService.class);
	private IClientDAO clientDAO = ServiceLocator.locateCurrent(IClientDAO.class);

	private ApplicationSettings settings = null;
	private Text clientFirstNameText;
	private Text clientLastNameText;
	private Text clientOrganizationText;

	/**
	 * 
	 */
	public ClientsView() {
		searchCriteria.setUser(securityService.getCurrentlyLoggedInUser());
		clients = new ArrayList<Client>();
		settings = securityService.getCurrentlyLoggedInUser().getSettings();
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new BorderLayout(0, 0));
		
		clientComposite = new Composite(container, SWT.NONE);
		clientComposite.setLayoutData(BorderLayout.WEST);
		clientComposite.setLayout(new swing2swt.layout.BorderLayout(0, 0));

		tableViewer = new TableViewer(clientComposite, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			/**
			 * Handles user selecting new PosEntry from list of entries.
			 */
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection transaction = (IStructuredSelection) tableViewer.getSelection();
				Client client = (Client) transaction.getFirstElement();
				if (client != null) {
					setControlsToEnabled();
				}
			}
		});
		clientTable = tableViewer.getTable();
		clientTable.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));

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
				ClientSearchCriteriaDialog dialog = new ClientSearchCriteriaDialog(getViewSite().getShell(), searchCriteria);
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
				addNewClient();
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
				deleteClient();
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
		new Label(childComposite, SWT.NONE);
		new Label(childComposite, SWT.NONE);
		
		Label lblFirstName = new Label(childComposite, SWT.NONE);
		lblFirstName.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblFirstName.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblFirstName.setText("Client First Name");
		
		clientFirstNameText = new Text(childComposite, SWT.BORDER);
		clientFirstNameText.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		clientFirstNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblLastName = new Label(childComposite, SWT.NONE);
		lblLastName.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblLastName.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblLastName.setText("Client Last Name");
		
		clientLastNameText = new Text(childComposite, SWT.BORDER);
		clientLastNameText.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		clientLastNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblOrganization = new Label(childComposite, SWT.NONE);
		lblOrganization.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblOrganization.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblOrganization.setText("Client Organization");
		
		clientOrganizationText = new Text(childComposite, SWT.BORDER);
		clientOrganizationText.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		clientOrganizationText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		// set other bindings
		initDataBindings();

		scrolledComposite.setContent(childComposite);
		scrolledComposite.setMinSize(childComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				
		reloadEntriesBasedOnCriteria();
	}


	/**
	 * @param customer
	 */
	private void addNewClient() {
		Client client = new Client();
		client.setFirstName("First Name");
		client.setLastName("Last Name");
		client.setUser(securityService.getCurrentlyLoggedInUser());
		clientDAO.persist(client);
		clients.add(client);
		WritableList writableList = new WritableList(clients, Client.class);
		tableViewer.setInput(writableList);
		IStructuredSelection sel = new StructuredSelection(client);
		tableViewer.setSelection(null);
		tableViewer.setSelection(sel);
		tableViewer.refresh();
	}
	
	/**
	 * @param customer
	 */
	private void deleteClient() {
		IStructuredSelection transaction = (IStructuredSelection) tableViewer.getSelection();
		Client client = (Client) transaction.getFirstElement();		
		clients.clear();
		clientDAO.delete(client);
		reloadEntriesBasedOnCriteria();
		tableViewer.setSelection(null);
	}
	
	private void setControlsToEnabled(){
		clientFirstNameText.setEnabled(true);
		clientLastNameText.setEnabled(true);
		clientOrganizationText.setEnabled(true);
	}

	private void setControlsToDisabled() {
		clientFirstNameText.setEnabled(false);
		clientLastNameText.setEnabled(false);
		clientOrganizationText.setEnabled(false);		
	}

	/**
	 * 
	 */
	private void reloadEntriesBasedOnCriteria() {
		page = 0;
		clients = clientDAO.getSearchListForPage(searchCriteria, settings.getDefaultSortOrder(), page, 
				settings.getNumberOfItemsToShowPerPage());
		WritableList writableList = new WritableList(clients, Client.class);
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
						clients = clientDAO.getSearchListForPage(searchCriteria, settings.getDefaultSortOrder(), page, 
								settings.getNumberOfItemsToShowPerPage());
						WritableList writableList = new WritableList(clients, Client.class);
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
						clients = clientDAO.getSearchListForPage(searchCriteria, settings.getDefaultSortOrder(), page, 
								settings.getNumberOfItemsToShowPerPage());
						WritableList writableList = new WritableList(clients, Client.class);
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
		long currentPageCount = clients.size();
		if (currentPageCount < settings.getNumberOfItemsToShowPerPage()
				|| (currentPageCount == settings.getNumberOfItemsToShowPerPage() && clientDAO
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

	class ClientUpdateStrategy extends UpdateValueStrategy {

		public ClientUpdateStrategy() {
			super(UpdateValueStrategy.POLICY_UPDATE);
		}
		
		public ClientUpdateStrategy(IConverter converter) {
			super(UpdateValueStrategy.POLICY_UPDATE);
			setConverter(converter);
		}

		protected IStatus doSet(IObservableValue observableValue, final Object value) {
			IStatus ret = super.doSet(observableValue, value);
			if (ret.isOK() && value != null && !value.equals("")) {
				Realm.getDefault().asyncExec(new Runnable() {
					public void run() {
						IStructuredSelection transaction = (IStructuredSelection) tableViewer.getSelection();
						Client client = (Client) transaction.getFirstElement();
						if (client != null) {
							client = clientDAO.merge(client);
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
		IObservableMap observeMap = BeansObservables.observeMap(listContentProvider.getKnownElements(), Client.class, "label");
		tableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMap));
		tableViewer.setContentProvider(listContentProvider);
		//
		WritableList writableList = new WritableList(clients, Client.class);
		tableViewer.setInput(writableList);
		//
		IObservableValue tableViewerObserveSingleSelection = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue tableViewerFirstNameObserveDetailValue = PojoObservables.observeDetailValue(tableViewerObserveSingleSelection, "firstName", String.class);
		IObservableValue clientFirstNameTextObserveTextObserveWidget = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(clientFirstNameText, SWT.Modify));
		m_bindingContext.bindValue(tableViewerFirstNameObserveDetailValue, clientFirstNameTextObserveTextObserveWidget, null, new ClientUpdateStrategy());
		//
		IObservableValue tableViewerObserveSingleSelection_2 = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue tableViewerLastNameObserveDetailValue = PojoObservables.observeDetailValue(tableViewerObserveSingleSelection_2, "lastName", String.class);
		IObservableValue clientLastNameTextObserveTextObserveWidget = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(clientLastNameText, SWT.Modify));
		m_bindingContext.bindValue(tableViewerLastNameObserveDetailValue, clientLastNameTextObserveTextObserveWidget, null, new ClientUpdateStrategy());
		//
		IObservableValue tableViewerObserveSingleSelection_3 = ViewersObservables.observeSingleSelection(tableViewer);
		IObservableValue tableViewerOrganizationObserveDetailValue = PojoObservables.observeDetailValue(tableViewerObserveSingleSelection_3, "organization", String.class);
		IObservableValue clientOrganizationTextObserveTextObserveWidget = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(clientOrganizationText, SWT.Modify));
		m_bindingContext.bindValue(tableViewerOrganizationObserveDetailValue, clientOrganizationTextObserveTextObserveWidget, null, new ClientUpdateStrategy());
		//
		return m_bindingContext;
	}
}
