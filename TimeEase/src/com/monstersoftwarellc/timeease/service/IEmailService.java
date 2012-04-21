package com.monstersoftwarellc.timeease.service;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;


public interface IEmailService {

	
    /**
     * Will attempt to send the given information at once. Must provide a valide TO address.
     * @param to
     * @param subject
     * @param body
     * @throws MessagingException
     * @throws MailException
     */
    public abstract void sendMail(String to,String subject,String body) throws MessagingException,MailException;
    
    /**
     * Will attempt to send the given information at once. Will use the {@link ApplicationSettingsCommand#getEmailAddressForBugSubmission()}.
     * @param subject
     * @param body
     * @throws MessagingException
     * @throws MailException
     */
    public abstract void sendMailForBug(String subject,String body) throws MessagingException,MailException;
   

}