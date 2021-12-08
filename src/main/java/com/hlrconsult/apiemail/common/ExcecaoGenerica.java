package com.hlrconsult.apiemail.common;

import org.springframework.http.HttpStatus;

public class ExcecaoGenerica extends Exception {
	private static final long serialVersionUID = 1L;
	protected final HttpStatus httpCodigoStatus;

	public ExcecaoGenerica(HttpStatus httpCodigoStatus, String mensagem) {
		super(mensagem);
		this.httpCodigoStatus = httpCodigoStatus;
	}

	public HttpStatus getHttpCodigoStatus() {
		return httpCodigoStatus;
	}
}
