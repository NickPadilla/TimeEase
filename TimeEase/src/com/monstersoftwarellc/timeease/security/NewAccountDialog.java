package com.monstersoftwarellc.timeease.security;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.service.IAccountService;
import com.monstersoftwarellc.timeease.service.impl.ServiceLocator;
import com.monstersoftwarellc.timeease.utility.PasswordUtility;

public class NewAccountDialog extends TitleAreaDialog {
	
	private Text firstNameText;
	private Text lastNameText;
	private Text loginNameText;
	private Text passwordText;
	private Text passwordVerifyText;
	
	private IAccountService accountService;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public NewAccountDialog(Shell parentShell) {
		super(parentShell);
		accountService = ServiceLocator.locateCurrent(IAccountService.class);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label lblFirstName = new Label(container, SWT.NONE);
		lblFirstName.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblFirstName.setText("First Name");
		
		firstNameText = new Text(container, SWT.BORDER);
		firstNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblLastName = new Label(container, SWT.NONE);
		lblLastName.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblLastName.setText("Last Name");
		
		lastNameText = new Text(container, SWT.BORDER);
		lastNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblUsername = new Label(container, SWT.NONE);
		lblUsername.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblUsername.setText("Login Name");
		
		loginNameText = new Text(container, SWT.BORDER);
		loginNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPassword = new Label(container, SWT.NONE);
		lblPassword.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblPassword.setText("Password");
		
		passwordText = new Text(container, SWT.BORDER | SWT.PASSWORD);
		passwordText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPasswordVerify = new Label(container, SWT.NONE);
		lblPasswordVerify.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPasswordVerify.setText("Password Verify");
		
		passwordVerifyText = new Text(container, SWT.BORDER | SWT.PASSWORD);
		passwordVerifyText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button ok = new Button(container, SWT.NONE);
		ok.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		ok.setText("Save");
		ok.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(loginNameText.getText().isEmpty() || passwordText.getText().isEmpty() || passwordVerifyText.getText().isEmpty()){
					MessageDialog.openError(getShell(), "Missing Data!", "Please fill out Login Name and Password fields!");					
				}else{
					if(accountService.getAccountRepository().findByUsername(loginNameText.getText()) == null){
						if(passwordVerifyText.getText().equals(passwordText.getText())){
							// encrypt password and save user
							Account user = new Account();
							user.setFirstName(firstNameText.getText());
							user.setLastName(lastNameText.getText());
							user.setPassword(PasswordUtility.encodePassword(passwordText.getText()));
							user.setPasswordVerify(PasswordUtility.encodePassword(passwordVerifyText.getText()));
							user.setUsername(loginNameText.getText());
							accountService.getAccountRepository().saveAndFlush(user);
							okPressed();
						}else{
							// passwords don't match
							MessageDialog.openError(getShell(), "Passwords don't match!", "Please fix passwords!");
						}
					}else{
						// username taken 
						MessageDialog.openError(getShell(), "Username Taken!", "Please select another username as '" + loginNameText.getText() + "' is taken!");
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {		
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(451, 381);
	}
}
