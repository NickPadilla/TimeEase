/**
 * 
 */
package com.monstersoftwarellc.timeease.utility;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.monstersoftwarellc.timeease.model.GMailAuthenticator;


/**
 * @author nicholas
 *
 */
public class MailUtil {
	
	public static final String SMTP_URL = "smtp.gmail.com";

	public static final int SMTP_PORT = 465;
	
	public static final String REPORT_A_TIMEEASE_BUG_TO = "monstersoftwarellc@tickets.assembla.com";
	
	public static final String REPORT_A_TIMEEASE_BUG_FROM = "nicholas@monstersoftwarellc.com";
    
	public static final String EMAIL_USER_FROM_PASS = "H0m3Fr3E!";
	
	
	
    /**
     * @param subject
     * @param body
     * @throws MessagingException
     */
    public static void sendMail(String subject, String body) throws MessagingException {
		
    	Session session = Session.getInstance(getProperties(),new GMailAuthenticator(REPORT_A_TIMEEASE_BUG_FROM, "H0m3Fr3E!"));

    	MimeMessage message = buildMessage(session, subject, body);
    	
    	// send immediately
    	doSendMail(session, message);
    }

	/**
	 * @return
	 */
	private static Properties getProperties() {
		Properties props = new Properties();
    	props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_URL);
        props.put("mail.smtp.user", REPORT_A_TIMEEASE_BUG_FROM);
        props.put("mail.smtp.password", EMAIL_USER_FROM_PASS);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		return props;
	}
   
    /**
     * @param session
     * @param subject
     * @param body
     * @return
     * @throws AddressException
     * @throws MessagingException
     */
    private static MimeMessage buildMessage(Session session, String subject, String body) throws AddressException, MessagingException {
    	
    	MimeMessage message = new MimeMessage(session);
    	message.setRecipient(Message.RecipientType.TO,new InternetAddress(REPORT_A_TIMEEASE_BUG_TO));
    	message.setFrom(new InternetAddress(REPORT_A_TIMEEASE_BUG_FROM));
    	message.setSubject(subject);
    	message.setText(body);

    	return message;
    }
    

    /**
     * @param session
     * @param message
     * @throws MessagingException
     */
    private static void doSendMail(Session session, MimeMessage message) throws MessagingException {
    	
    	Transport transport = session.getTransport("smtp");
		transport.connect(SMTP_URL, REPORT_A_TIMEEASE_BUG_FROM, EMAIL_USER_FROM_PASS);
		transport.sendMessage(message, message.getAllRecipients());
		
		transport.close();
    }

}
