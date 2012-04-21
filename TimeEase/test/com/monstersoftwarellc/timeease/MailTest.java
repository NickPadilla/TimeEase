/**
 * 
 */
package com.monstersoftwarellc.timeease;

import javax.mail.MessagingException;

import com.monstersoftwarellc.timeease.utility.MailUtil;

/**
 * @author nicholas
 *
 */
public class MailTest {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String subject = "Testing";
		String message = "testing 'Report A Bug' functionality, added via email! \n .";
		
		try {
			MailUtil.sendMail(subject, message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
