package com.hlrconsult.apiemail.dtos;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EmailDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "A identificação da aplicação deverá ser informada.")
	private String ownerRef;
	@NotBlank(message = "O e-mail do emissor deve ser informado.")
	@Email(message = "E-mail do emissor informado é inválido")
	private String emailFrom;
	@NotBlank(message = "O e-mail de destino deve ser informado.")
	@Email(message = "E-mail do destinatario informado é inválido")
	private String emailTo;
	@NotBlank(message = "O asunto do e-mail deve ser informado.")
	private String subject;
	@NotBlank
	private String text;

}
