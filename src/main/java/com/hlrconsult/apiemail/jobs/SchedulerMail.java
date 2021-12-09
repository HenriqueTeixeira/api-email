package com.hlrconsult.apiemail.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hlrconsult.apiemail.common.ExcecaoGenerica;
import com.hlrconsult.apiemail.services.EmailService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@EnableScheduling
@Component
public class SchedulerMail {

	@Autowired
	EmailService emailService;

	@Scheduled(fixedDelay = 3 * 60 * 1000)
	public void processarArquivosRetorno() throws ExcecaoGenerica {
		log.info("Inicio do scheduler de envio de e-mail.");
		emailService.sendEmailByScheduler();
		log.info("Fim do scheduler de envio de e-mail.");
	}
}
