package com.monstersoftwarellc.timeease.service.impl;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.monstersoftwarellc.timeease.service.IEmailService;


/**
 * @author nicholas
 *
 */
@Service
public class EmailService implements IEmailService {

	private static Logger LOG = Logger.getLogger(EmailService.class);

	public static final String GMAIL_SMTP_URL = "smtp.gmail.com";

	public static final int DEFAULT_SMTP_PORT = 465;
	
	public static final String REPORT_A_TIMEEASE_BUG_TO = "monstersoftwarellc@tickets.assembla.com";
	
	public static final String REPORT_A_TIMEEASE_BUG_FROM = "timeease@monstersoftwarellc.com";
    
	public static final String EMAIL_USER_FROM_PASS = "H0m3Fr3E!";

	private JavaMailSenderImpl mailSender = new JavaMailSenderImpl(); 

	private ExecutorService es;

	@PostConstruct
	public void intialize(){
		es = Executors.newCachedThreadPool();
	}

	/* (non-Javadoc)
	 * @see com.goldrush.service.IEmailService#sendMail(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void sendMail(String to,String subject,String body) throws MessagingException,MailException{
		InternetAddress from = getFrom();
		MimeMessage message = buildMail(new InternetAddress(to), from, subject, body);
		queue(message);
	}

	/* (non-Javadoc)
	 * @see com.goldrush.service.IEmailService#sendMailForBug(java.lang.String, java.lang.String)
	 */
	@Override
	public void sendMailForBug(String subject, String body)
			throws MessagingException, MailException {
		InternetAddress from = getFrom();
		String to = REPORT_A_TIMEEASE_BUG_TO;
		MimeMessage message = buildMail(new InternetAddress(to), from, subject, body);
		queue(message);
	}
	
	/**
	 * @param subject
	 * @param body
	 * @param sendTo
	 * @throws MessagingException
	 * @throws MailException
	 */
	public void sendQueuedMail(String subject, String body, Collection<InternetAddress> sendTo)
			throws MessagingException, MailException {
		InternetAddress from = getFrom();
        
        for( InternetAddress recipient : sendTo) {
        	MimeMessage message = buildMail(recipient, from, subject, body);
        	queue( message);
        }
	}

	/**
	 * Queues the given message to be sent in the background. 
	 * @param message
	 */
	private void queue(MimeMessage message) {

		LOG.debug("++ Queuing Email");
		es.execute( new SendRunnable( message ) );
	}

	/**
	 * @param to
	 * @param from
	 * @param subject
	 * @param body
	 * @return
	 * @throws MessagingException
	 * @throws MailException
	 */
	private MimeMessage buildMail(InternetAddress to, InternetAddress from, String subject,String body) throws MessagingException,MailException{
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(to);
		helper.setFrom(from);
		helper.setSubject(subject);
		helper.setText(body);
		return message;
	}

	/**
	 * @return
	 * @throws AddressException
	 */
	private InternetAddress getFrom()throws AddressException{
		return new InternetAddress( REPORT_A_TIMEEASE_BUG_FROM );
	}

	/**
	 * @param message
	 * @throws MailException
	 */
	private void doSendMail(MimeMessage message) throws MailException{
		mailSender.setHost(GMAIL_SMTP_URL); 	
		mailSender.setUsername(REPORT_A_TIMEEASE_BUG_FROM);
		mailSender.setPassword(EMAIL_USER_FROM_PASS);    
		mailSender.send(message);	
	}

	private class SendRunnable implements Runnable {
		private MimeMessage message;
		private SendRunnable(MimeMessage message) {
			this.message = message;
		}
		public void run() {
			LOG.debug("-- Sending Email");
			try {
				doSendMail(message);
			} catch (MailException e) {
				LOG.debug("Error Sending mail",e);
			}
		}
	}
}