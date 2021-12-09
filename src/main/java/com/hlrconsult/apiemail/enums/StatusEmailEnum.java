package com.hlrconsult.apiemail.enums;

public enum StatusEmailEnum {

	PENDING(0), PROCESSING(1), ERROR(2), SENT(3), NOT_SENT(4);

	private int status;

	StatusEmailEnum(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}
