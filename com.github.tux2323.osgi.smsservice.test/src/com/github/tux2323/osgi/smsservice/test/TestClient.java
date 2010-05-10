package com.github.tux2323.osgi.smsservice.test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.github.tux2323.osgi.smsservice.SmsService;

public class TestClient {

	private SmsService smsService;
	
	JFrame smsClientFrame;
	JTextField numberField;
	JTextArea message;
	
	public void start(){
		smsClientFrame = new JFrame("SMS Client App");
		Container contentPane = smsClientFrame.getContentPane();
		
		JPanel numberPanel = new JPanel();
		numberPanel.setLayout(new GridLayout(-1, 1));
		numberPanel.add(new JLabel("Mobil Nummer: "));
		numberField = new JTextField();
		numberPanel.add(numberField);
		numberPanel.add(new JLabel("Nachricht: "));
		contentPane.add(numberPanel, BorderLayout.NORTH);
		
		message = new JTextArea();
		message.setRows(5);
		contentPane.add(new JScrollPane(message), BorderLayout.CENTER);
		
		JButton sendButton = new JButton("Senden");
		contentPane.add(sendButton, BorderLayout.SOUTH);
		sendButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String number = numberField.getText();
				String msg = message.getText();
				try
				{
					smsService.sendSms(number, msg);
					JOptionPane.showMessageDialog(smsClientFrame, "SMS send successful done.");
				}
				catch(Exception exp){
					JOptionPane.showMessageDialog(smsClientFrame, "Error on send SMS !!!", "SMS Send Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		smsClientFrame.pack();
		smsClientFrame.setVisible(true);
	}
	
	public void stop(){
		smsClientFrame.setVisible(false);
	}
	
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	public SmsService getSmsService() {
		return smsService;
	}
	
	
	public static void main(String[] args) {
		TestClient testClient = new TestClient();
		testClient.start();
	}
}
