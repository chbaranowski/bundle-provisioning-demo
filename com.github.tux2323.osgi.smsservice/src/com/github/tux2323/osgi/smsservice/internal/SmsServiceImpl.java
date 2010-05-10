package com.github.tux2323.osgi.smsservice.internal;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tux2323.osgi.smsservice.SmsService;
import com.github.tux2323.osgi.smsservice.configuration.SmsServiceConfiguration;
import com.telekom.developer.Environment;
import com.telekom.developer.sms.clientsdk.SendSmsResponse;
import com.telekom.developer.sms.clientsdk.SmsClient;

public class SmsServiceImpl implements SmsService {

	private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
	
	private SmsServiceConfiguration configuration;
	
	public SmsServiceImpl() {
		logger.debug("New SMS service created");
	}
	
	public void setConfiguration(SmsServiceConfiguration configuration) {
		this.configuration = configuration;
	}

	public SmsServiceConfiguration getConfiguration() {
		return configuration;
	}

	public void sendSms(String number, String message) {
		try {
			logger.info("Send SMS with number : " + number + " and message : " + message);
			SmsClient smsClient = new SmsClient(configuration.getUsername(), configuration.getPassword(), Environment.SANDBOX);
			SendSmsResponse sendSMS = smsClient.sendSMS(number, message, "Christian");
			logger.info("SMS Send Status: " + sendSMS.getStatus());
			if(!sendSMS.getStatus().equals("0000")){
				logger.error("SMS Send NOT Successful !");
				throw new RuntimeException("SMS Send Error");
			}
			logger.info("SMS Send Successful");
		} catch (RemoteException e) {
			logger.error("SMS Send faild :", e);
			throw new RuntimeException("SMS Send Error");
		} catch (ServiceException e) {
			logger.error("SMS Send faild :", e);
			throw new RuntimeException("SMS Send Error");
		}
	}
	
}
