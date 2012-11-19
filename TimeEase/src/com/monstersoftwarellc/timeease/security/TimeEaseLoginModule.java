package com.monstersoftwarellc.timeease.security;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextOutputCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.service.IAccountService;
import com.monstersoftwarellc.timeease.service.impl.ServiceLocator;


/**
 * @author nicholas
 * 
 */
public class TimeEaseLoginModule implements LoginModule {

	private CallbackHandler callbackHandler;
	private boolean loggedIn;
	private Subject subject;
	private Account user;

	public TimeEaseLoginModule() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.security.auth.spi.LoginModule#initialize(javax.security.auth.Subject
	 * , javax.security.auth.callback.CallbackHandler, java.util.Map,
	 * java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map sharedState, Map options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.security.auth.spi.LoginModule#login()
	 */
	public boolean login() throws LoginException {
		Callback label = new TextOutputCallback(TextOutputCallback.INFORMATION,
				"Please login to Time Ease!");

		NameCallback nameCallback = new NameCallback("Username:");
		PasswordCallback passwordCallback = new PasswordCallback("Password:",
				false);

		try {
			callbackHandler.handle(new Callback[] { label, nameCallback,
					passwordCallback });
		} catch (Exception e) {
			e.printStackTrace();
		}

		user = ServiceLocator.locateCurrent(IAccountService.class)
							 	 .authenticate(nameCallback.getName(), 
									 	   	   String.valueOf(passwordCallback.getPassword()));
		if(user != null){
			loggedIn = true;
		}
		
		return loggedIn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.security.auth.spi.LoginModule#commit()
	 */
	public boolean commit() throws LoginException {
		subject.getPublicCredentials().add(user);
		subject.getPrivateCredentials().add(Display.getCurrent());
		subject.getPrivateCredentials().add(SWT.getPlatform());
		return loggedIn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.security.auth.spi.LoginModule#abort()
	 */
	public boolean abort() throws LoginException {
		loggedIn = false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.security.auth.spi.LoginModule#logout()
	 */
	public boolean logout() throws LoginException {
		loggedIn = false;
		return true;
	}
}
