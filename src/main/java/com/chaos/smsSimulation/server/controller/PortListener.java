/**
 * Top Controller of server side app.
 * 
 * Copyright (c) 2014 BIT-SmsSimulation.
 * 
 * This file is part of BIT-SmsSimulation.
 * 
 * BIT-SmsSimulation is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * BIT-SmsSimulation is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with BIT-SmsSimulation.  If not, see <http://www.gnu.org/licenses/>.
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
