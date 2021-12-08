package com.hlrconsult.apiemail.consumers;


import javax.validation.Valid;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.hlrconsult.apiemail.configs.RabbitMQConfig;
import com.hlrconsult.apiemail.dtos.EmailDTO;
import com.hlrconsult.apiemail.models.EmailModel;
import com.hlrconsult.apiemail.services.EmailService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class EmailConsumer {

	@Autowired
	EmailService emailService;

	@RabbitListener(queues = RabbitMQConfig.MAIL_QUEUE)
	private void listen(@Payload @Valid EmailDTO emailDto){
		log.info("Escutando a fila de e-mail.");
		EmailModel emailModel = new EmailModel();
		BeanUtils.copyProperties(emailDto, emailModel);
		emailService.sendEmail(emailModel);
	}
}
