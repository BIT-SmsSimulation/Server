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
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chaos.smsSimulation.server.model.Message;
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
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				String content;
				while ((content = reader.readLine()) != null) {
					switch (content) {
					case "M":
						String senderNum = readPhoneNum(reader);
						String receiverNum = readPhoneNum(reader);
						String msgContent = "";
						boolean newlineFlag = false;
						while (!(content = reader.readLine()).equals(MessageService.END_OF_MSG)) {
							if (newlineFlag) {
								msgContent += "\n";
							} else {
								newlineFlag = true;
							}
							msgContent += content;
						}
						
						Message msg = new Message();
						msg.setContent(msgContent);
						msg.setSenderNum(senderNum);
						msg.setReceiverNum(receiverNum);
						if (messageService.sendMessage(msg)) {
							LOGGER.info("Message from " + senderNum + " to " + receiverNum + " has been sent successfully");
							messageService.sendReceipt(msg, MessageService.RESULT_SUCCESS);
							msg.setStatus(Message.SENT);
						} else {
							LOGGER.info("Message from " + senderNum + " to " + receiverNum + " failed to be sent");
							LOGGER.info("Saving message to db...");
							messageService.sendReceipt(msg, MessageService.RESULT_SENDING);
							msg.setStatus(Message.SENDING);
						}
						
						messageService.saveMessage(msg);
						break;
						
					case "U":
						String num = readPhoneNum(reader);
						
						if ((content = reader.readLine()) == null) {
							throw new Exception("Format error");
						} else {
							String ip = socket.getInetAddress().getHostAddress();
							switch (content) {
							case "login":
								if (userService.login(num, ip)) {
									LOGGER.info(num + "@" + ip + ": login success");
									LOGGER.info("Sending unsent messages to this user...");
									messageService.sendUnsentMessages(num);
								} else {
									LOGGER.error(num + "@" + ip + ": login failure");
								}
								break;
								
							case "logoff":
								if (userService.logoff(num, ip)) {
									LOGGER.info(num + "@" + ip + ": logoff success");
								} else {
									LOGGER.error(num + "@" + ip + ": logoff failure");
								}
								break;

							default:
								throw new Exception("Format error");
							}
						}
						break;
						
					case "":
						break;

					default:
						throw new Exception("Format error");
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private String readPhoneNum(BufferedReader reader) throws Exception {
			String line;
			
			if ((line = reader.readLine()) == null) {
				throw new Exception("Format error");
			} else if (!line.matches("[0-9]{11}")) {
				throw new Exception("Format error");
			}
			return line;
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
