package com.hlrconsult.apiemail.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hlrconsult.apiemail.dtos.EmailDTO;
import com.hlrconsult.apiemail.enums.StatusEmailEnum;
import com.hlrconsult.apiemail.models.EmailModel;
import com.hlrconsult.apiemail.services.EmailService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class EmailController {

	@Autowired
	EmailService emailService;

	@PostMapping("/enviar-email")
	public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDTO emailDTO) {
		log.info(emailDTO.getOwnerRef() + " enviou um e-mail para " + emailDTO.getEmailTo());
		EmailModel email = new EmailModel();
		BeanUtils.copyProperties(emailDTO, email);
		emailService.sendEmail(email);
		return new ResponseEntity<>(email, HttpStatus.CREATED);
	}

	@PostMapping("/enviar-email-anexo")
	public ResponseEntity<EmailModel> sendingEmailWith(@RequestBody @Valid EmailDTO emailDTO) {
		log.info(emailDTO.getOwnerRef() + " enviou um e-mail para " + emailDTO.getEmailTo());
		EmailModel email = new EmailModel();
		BeanUtils.copyProperties(emailDTO, email);
		emailService.sendEmailWithAttachment(email);
		return new ResponseEntity<>(email, HttpStatus.CREATED);
	}

	@PostMapping("/enviar-agendamento-email-lote")
	public ResponseEntity<List<EmailModel>> sendingEmailByScheduler(@RequestBody @Valid List<EmailDTO> emailListDTO) {
		log.info("Recebendo e-mails para envio em lote.");

		List<EmailModel> emailList = new ArrayList<>();

		emailListDTO.stream().forEach(emailDTO -> {
			EmailModel email = new EmailModel();
			BeanUtils.copyProperties(emailDTO, email);
			email.setStatusEmail(StatusEmailEnum.PENDING);
			emailList.add(email);
		});
		emailService.saveEmailByScheduler(emailList);
		return new ResponseEntity<>(emailList, HttpStatus.CREATED);
	}
}
