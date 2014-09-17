/**
 * 
 */
package com.chaos.smsSimulation.server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chaos.smsSimulation.server.service.MessageService;
import com.chaos.smsSimulation.server.service.UserService;

/**
 * @author chaos
 *
 */
public class PortListener {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(PortListener.class);
	
	private MessageService messageService;
	private UserService userService;

	public void startListen() throws Exception {
		
		ServerSocket serverSocket = new ServerSocket(MessageService.RECEIVE_PORT);
		
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				new Thread(new ServerThread(socket)).run();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	class ServerThread implements Runnable {
		
		private Socket socket;
		
		public ServerThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				LOGGER.info(socket.getInetAddress().toString());
				LOGGER.info(String.valueOf(socket.getPort()));
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				String content;
				while ((content = reader.readLine()) != null) {
					LOGGER.info(content);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public Socket getSocket() {
			return socket;
		}

		public void setSocket(Socket socket) {
			this.socket = socket;
		}
		
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
