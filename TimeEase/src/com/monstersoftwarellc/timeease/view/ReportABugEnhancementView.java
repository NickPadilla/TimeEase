/**
 * 
 */
package com.monstersoftwarellc.timeease.view;

import javax.mail.MessagingException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.springframework.mail.MailException;

import com.monstersoftwarellc.timeease.service.IEmailService;
import com.monstersoftwarellc.timeease.service.ServiceLocator;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * @author nicholas
 * 
 */
public class ReportABugEnhancementView extends ViewPart {

	public static final String ID = "com.monstersoftwarellc.timeease.views.ReportABugEnhancementView"; //$NON-NLS-1$
	private Label ticketHeaderLabel;
	private Text subjectText;
	private Label ticketSummaryLabel;

	public ReportABugEnhancementView() {
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(4, true));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		ticketHeaderLabel = new Label(container, SWT.NONE);
		ticketHeaderLabel.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		/*ticketHeaderLabel.setFont(SWTResourceManager.getFont("Ubuntu", 12,
				SWT.BOLD));*/
		ticketHeaderLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER,
				false, false, 1, 1));
		ticketHeaderLabel.setText("Submit Ticket Title");

		subjectText = new Text(container, SWT.BORDER);
		subjectText.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		subjectText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 3, 1));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		ticketSummaryLabel = new Label(container, SWT.NONE);
		ticketSummaryLabel.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		/*ticketSummaryLabel.setFont(SWTResourceManager.getFont("Ubuntu", 12,
				SWT.BOLD));*/
		ticketSummaryLabel.setAlignment(SWT.CENTER);
		ticketSummaryLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER,
				false, false, 2, 1));
		ticketSummaryLabel.setText("Submit Ticket Summary");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		final StyledText ticketSummaryText = new StyledText(container,
				SWT.BORDER | SWT.WRAP);
		ticketSummaryText.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		GridData gd_ticketSummaryText = new GridData(SWT.FILL, SWT.FILL, true,
				true, 4, 1);
		gd_ticketSummaryText.widthHint = 291;
		ticketSummaryText.setLayoutData(gd_ticketSummaryText);
		new Label(container, SWT.NONE);

		Button cancelButton = new Button(container, SWT.NONE);
		cancelButton.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		GridData gd_cancelButton = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1);
		gd_cancelButton.widthHint = 143;
		cancelButton.setLayoutData(gd_cancelButton);
		cancelButton.setText("Cancel");
		cancelButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				for (IViewReference ref : page.getViewReferences()) {
					if (ID.equals(ref.getId())) {
						page.hideView(ref);
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		Button sendButton = new Button(container, SWT.NONE);
		sendButton.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		GridData gd_sendButton = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1);
		gd_sendButton.widthHint = 141;
		sendButton.setLayoutData(gd_sendButton);
		sendButton.setText("Send");
		sendButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				final String summary = ticketSummaryText.getText();
				final String header = subjectText.getText();
				if (summary != null && !summary.isEmpty() && header != null
						&& !header.isEmpty()) {
					Job job = new Job("Sending Ticket..") {
						@Override
						protected IStatus run(IProgressMonitor monitor) {
							monitor.beginTask("Sending ticket...", 100);
							// execute the task ...
							try {
								ServiceLocator.locateCurrent(
										IEmailService.class).sendMailForBug(
										header, summary);
							} catch (MailException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalStateException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (MessagingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							monitor.done();
							return Status.OK_STATUS;
						}
					};
					job.schedule();
					IWorkbenchPage page = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage();
					for (IViewReference ref : page.getViewReferences()) {
						if (ID.equals(ref.getId())) {
							page.hideView(ref);
						}
					}
				} else {
					MessageBox box = new MessageBox(getViewSite().getShell(),
							SWT.OK | SWT.ICON_ERROR);
					box.setMessage("Cannot send ticket! Header and Summary are required!");
					box.setText("Input Exception!");
					box.open();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		new Label(container, SWT.NONE);

	}

	@Override
	public void setFocus() {
		// Set the focus
	}
}
