package com.monstersoftwarellc.timeease.security;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextOutputCallback;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Handles the callbacks to show a RCP UI for the LoginModule.
 */
public class LoginCallbackHandler extends AbstractLoginDialog {

	/**
	 * @wbp.parser.constructor
	 */
	public LoginCallbackHandler() {
		this(Display.getDefault().getActiveShell());
	}

	protected LoginCallbackHandler(Shell parentShell) {
		super(parentShell);
	}

	protected Point getInitialSize() {
		return new Point(392, 334);
	}

	protected Control createDialogArea(final Composite parent) {
		Composite dialogarea = (Composite) super.createDialogArea(parent);
		dialogarea.setLayoutData(new GridData(GridData.FILL_BOTH));
		Composite composite = new Composite(dialogarea, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		composite.setLayout(new GridLayout());
		createCallbackHandlers(composite);
		Button newAccountButton = new Button(dialogarea, SWT.NONE);
		newAccountButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		newAccountButton.setText("New Account");
		newAccountButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(final SelectionEvent event) {
				NewAccountDialog dialog = new NewAccountDialog(parent.getShell());
				dialog.open();
			}

			public void widgetDefaultSelected(final SelectionEvent event) {
				// nothing to do
			}
		});
		return composite;
	}

	private void createCallbackHandlers(Composite composite) {
		Callback[] callbacks = getCallbacks();
		for (int i = 0; i < callbacks.length; i++) {
			Callback callback = callbacks[i];
			if (callback instanceof TextOutputCallback) {
				createTextoutputHandler(composite,
						(TextOutputCallback) callback);
			} else if (callback instanceof NameCallback) {
				createNameHandler(composite, (NameCallback) callback);
			} else if (callback instanceof PasswordCallback) {
				createPasswordHandler(composite, (PasswordCallback) callback);
			}
		}
	}

	private void createPasswordHandler(Composite composite,
			final PasswordCallback callback) {
		Label label = new Label(composite, SWT.NONE);
		label.setText(callback.getPrompt());
		final Text passwordText = new Text(composite, SWT.SINGLE | SWT.LEAD
				| SWT.PASSWORD | SWT.BORDER);
		passwordText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		passwordText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent event) {
				callback.setPassword(passwordText.getText().toCharArray());
			}
		});
	}

	private void createNameHandler(Composite composite,
			final NameCallback callback) {
		Label label = new Label(composite, SWT.NONE);
		label.setText(callback.getPrompt());
		final Text text = new Text(composite, SWT.SINGLE | SWT.LEAD
				| SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent event) {
				callback.setName(text.getText());
			}
		});
	}

	private void createTextoutputHandler(Composite composite,
			TextOutputCallback callback) {
		int messageType = callback.getMessageType();
		int dialogMessageType = IMessageProvider.NONE;
		switch (messageType) {
		case TextOutputCallback.INFORMATION:
			dialogMessageType = IMessageProvider.INFORMATION;
			break;
		case TextOutputCallback.WARNING:
			dialogMessageType = IMessageProvider.WARNING;
			break;
		case TextOutputCallback.ERROR:
			dialogMessageType = IMessageProvider.ERROR;
			break;
		}
		setMessage(callback.getMessage(), dialogMessageType);
	}

	public void internalHandle() {
	}
}
