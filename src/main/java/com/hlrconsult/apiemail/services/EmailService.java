package com.hlrconsult.apiemail.services;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.hlrconsult.apiemail.enums.StatusEmailEnum;
import com.hlrconsult.apiemail.models.EmailModel;
import com.hlrconsult.apiemail.repositories.EmailRepository;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class EmailService {

	@Autowired
	EmailRepository emailRepository;

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private Configuration config;

	@SuppressWarnings("finally")
	public EmailModel sendEmail(EmailModel email) {
		email.setSendDateEmail(LocalDateTime.now());
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(email.getEmailFrom());
			message.setTo(email.getEmailTo());
			message.setSubject(email.getSubject());
			message.setText(email.getText());
			emailSender.send(message);

			email.setStatusEmail(StatusEmailEnum.SENT);
			log.info("e-mail enviado: " + email.getEmailTo());
		} catch (MailException e) {
			email.setStatusEmail(StatusEmailEnum.ERROR);
			log.error("falha no envio do e-mail: " + email.getEmailTo() + " Aplicacao: " + email.getOwnerRef());
		} finally {
			log.info("Envio do e-mail salvo.");
			return emailRepository.save(email);
		}
	}

	@SuppressWarnings("finally")
	public EmailModel sendEmailWithAttachment(EmailModel email) {
		MimeMessage message = emailSender.createMimeMessage();
		email.setSendDateEmail(LocalDateTime.now());
		try {

			// Here is defined the mediaType
			MimeMessageHelper messageHelper = new MimeMessageHelper(message,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

			// Here the attachment is added
			messageHelper.addAttachment("arquivo_exemplo_anexo.xml",
					new ClassPathResource("arquivo_exemplo_anexo.xml"));

			Map<String, Object> model = new HashMap<>();
			// model.put("caption", email.getOwnerRef());
			model.put("text", email.getText());

			Template t = config.getTemplate("email-template.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

			messageHelper.setFrom(email.getEmailFrom());
			messageHelper.setTo(email.getEmailTo());
			messageHelper.setSubject(email.getSubject());
			messageHelper.setText(html, true);
			emailSender.send(message);

			email.setStatusEmail(StatusEmailEnum.SENT);
			log.info("e-mail enviado: " + email.getEmailTo());
		} catch (MailException e) {
			email.setStatusEmail(StatusEmailEnum.ERROR);
			log.error("falha no envio do e-mail: " + email.getEmailTo() + " Aplicacao: " + email.getOwnerRef());
		} finally {
			log.info("Envio do e-mail salvo.");
			return emailRepository.save(email);
		}
	}

	public List<EmailModel> saveEmailByScheduler(List<EmailModel> emailList) {
		//TODO - Aplicação de validações
		log.info("Salvando e-mails para envio por scheduler.");
		return emailRepository.saveAll(emailList);
	}

	public void sendEmailByScheduler() {
		List<EmailModel> emailList = emailRepository.findByStatusEmail();
		emailList.stream().forEach(item -> item.setStatusEmail(StatusEmailEnum.PROCESSING));
		emailRepository.saveAll(emailList);
		emailList.stream().forEach(this::sendEmail);
	}
}
