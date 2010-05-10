package com.github.tux2323.osgi.smsservice;

public interface SmsService {

	void sendSms(String number, String message);
	
}
