package com.pra.payrollmanager.apputils.mailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailerService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Async
	public void sendEmail(String subject, String text, String... recievers) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(recievers);
		mail.setSubject(subject);
		mail.setText(text);
		this.sendEmail(mail);
	}

	public void sendEmail(SimpleMailMessage mail) {
		javaMailSender.send(mail);
	}

}
