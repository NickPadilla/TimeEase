/**
 * 
 */
package com.monstersoftwarellc.timeease.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.Security;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;

import sun.misc.BASE64Encoder;

import com.monstersoftwarellc.timeease.TimeEaseSession;
import com.monstersoftwarellc.timeease.integration.IntegrationType;
import com.monstersoftwarellc.timeease.integration.freshbooks.FreshBooksIntegration;
import com.monstersoftwarellc.timeease.model.enums.ResponseStatus;
import com.monstersoftwarellc.timeease.service.impl.ServiceLocator;
import com.monstersoftwarellc.timeease.service.impl.SettingsService;
import com.sun.net.ssl.internal.ssl.Provider;
 
/**
 * Utility contains all logic for connecting to FreshBooks. <br/>
 * @author nick
 *
 */
public class ConnectionUtility {
	
	
	/**
	 * This method will handle sending the supplied <code>request</code> <br/>
	 * to Freshbooks and returns a response in the form of an xml string.<br/>
	 * <b>Note: supplied <code>request</code> must be in UTF-8 encoding.
	 * @param request
	 * @return
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 * @throws ConfigurationException 
	 */
	public static String performRequest(String request) throws AuthenticationException, IllegalStateException, ConfigurationException {
		String ret = performRequestFreshbooks(request);
		int retries = 0;
		while((ret.equals("Fail") || ret.equals("UnknownHost") || ret.equals("Network Unreachable")) && retries < 5){
			ret = performRequestFreshbooks(request);
			retries++;
		}
		return ret;
	}


	/**
	 * This method will handle sending the supplied <code>request</code> <br/>
	 * to Freshbooks and returns a response in the form of an xml string.<br/>
	 * <b>Note: supplied <code>request</code> must be in UTF-8 encoding.
	 * @param request
	 * @return
	 * @throws AuthenticationException, IllegalStateException
	 * @throws ConfigurationException 
	 */
	private static String performRequestFreshbooks(String request) throws IllegalStateException, AuthenticationException, ConfigurationException {
		InputStreamReader streamReader = null;
		OutputStream out = null;
		HttpsURLConnection connection = null;
		BufferedReader bufferedReader = null;
		StringBuffer stringBuffer = new StringBuffer();
		FreshBooksIntegration details = null; 
		List<IntegrationType> integrations = ServiceLocator.locateCurrent(SettingsService.class).getApplicationSettings().getIntegrations();
		for(IntegrationType integration : integrations){
			// TODO: add way to configure the integration types
			/*if(integration instanceof FreshBooksIntegration){
				details = (FreshBooksIntegration) integration;
			}*/
		}
		
		if(details == null){
			throw new ConfigurationException("Unable to find Freshbooks connection information!");
		}
		
		try {
			System.out.println(request);
			//String urlString = "https://monstersoftwarellc.freshbooks.com/api/2.1/xml-in";
			String urlString = "https://" + details.getUrl() + ".freshbooks.com/api/2.1/xml-in";

			Security.addProvider(new Provider());

			HostnameVerifier hv = new HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					System.out.println("Warning: URL Host: "+arg0+" vs. "+arg1.getPeerHost());
					return true;
				}
			};

			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			HttpsURLConnection.setFollowRedirects(false);

			//String usernamePass = "c2d557527b456740d64c8e0635b57b36:" + "-";
			String usernamePass = details.getTokenOrPassword() + ":" + "-";
			String encoding = new BASE64Encoder().encode (usernamePass.getBytes());

			URL url = new URL(urlString);
			connection = (HttpsURLConnection)url.openConnection();
			connection.setRequestProperty("Authorization", "Basic "+encoding);
			connection.addRequestProperty("Account-Agent", "Time EaseFreshbooksPlugin");
			connection.addRequestProperty("Content-Type", "application/xml; charset=utf-8");
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);

			out = connection.getOutputStream();
			out.write( request.getBytes()  ); 
			out.flush(); 
			out.close(); 

			int responseCode = connection.getResponseCode(); 
			String responseMsg = connection.getResponseMessage(); 			

			if(responseCode == 200 || responseMsg.equalsIgnoreCase(ResponseStatus.SUCCESSFUL.getValue())){
				InputStream is = connection.getInputStream(); 
				streamReader = new InputStreamReader(is);
				bufferedReader = new BufferedReader(streamReader);
				String inputLine;

				while ((inputLine = bufferedReader.readLine()) != null){
					stringBuffer.append(inputLine);
				}
				System.out.println(stringBuffer.toString());
			}else{
				// always set state if this operation
				stringBuffer.append("Fail");
				if(responseCode == 401){
					// token/password invalid
					throw new AuthenticationException("Token Invalid!");
				}else if(responseCode == 302){
					// site not found
					throw new AuthenticationException("URL Invalid!");
				}else{
					throw new IllegalStateException("Freshbooks Responded But Response Code/ResponseMsg Was Not Valid! "
							+ "\n    reponseCode :" + responseCode
							+ "\n    responseMsg :" + responseMsg);
				}
			}
		} catch (UnknownHostException e) {			
			stringBuffer.append("UnknownHost");
		} catch (SocketException ex){
			stringBuffer.append("Network Unreachable");
		} catch (IOException e) {			
			e.printStackTrace();
		}finally{
			if(connection != null){
				connection.disconnect();
			}
			try {
				if(streamReader != null){
					streamReader.close();
				}
				if(out != null){
					out.close();
				}
				if(bufferedReader != null){
					bufferedReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}

		return stringBuffer.toString();
	}
	
	/**
	 * Check to see if we are online, ping google.com.
	 * @return
	 */
	public static boolean areWeOnline(){
		boolean ret = false;
			try {
				//make a URL to a known source
				URL url = new URL("http://www.google.com");
	
				//open a connection to that source
				HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();
	
				//trying to retrieve data from the source. If there
				//is no connection, this line will fail
				urlConnect.getContent();
				ret = true;
				// set the session. 
				TimeEaseSession.getCurrentInstance().setOnline(ret);
			} catch (UnknownHostException e) {
				// just hide this error, since we are handling this upstream anyway
				//e.printStackTrace();
			} catch (IOException e) {
				// just hide this error, since we are handling this upstream anyway
				//e.printStackTrace();
			}
		return ret;

	}
	
	public static void syncOnlineWithProgress(final String header, final String subTask, final boolean fork){
		ProgressMonitorDialog progress = new ProgressMonitorDialog(null);
		progress.setCancelable(true);
		try {
			progress.run(fork, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException {
					try {
						monitor.beginTask(header, IProgressMonitor.UNKNOWN);
						if(areWeOnline()){
							monitor.subTask(subTask);
							SyncFreshbooksStateUtility.sync();
						}else{
							monitor.subTask("No Internet detected, requested action not performed!");
						}
					} finally {
						monitor.done();
					}
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Method will use the {@link ConnectionDetails} in the {@link TimeEaseSession} to see if they are valid.
	 * Checks to see if we are online, then uses {@link FreshbooksClientService} to get a list of clients; only getting one item.
	 * Only checks credentials if we are online, will update the online field of the {@link TimeEaseSession}.
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 * @throws ConfigurationException 
	 */
	public static void checkFreshbooksCredentials() throws AuthenticationException, IllegalStateException, ConfigurationException{
		if(areWeOnline()){
			/*ClientRequest request = new ClientRequest();
			request.setItemsPerPage(1);
			request.setPage(1);
			request.setMethod(RequestMethods.LIST);
			request.setEntity(new Client());
			String requestString = FreshbooksRequestUtility.getRequest(request);
			ConnectionUtility.performRequest(requestString);*/
		}
	}

}