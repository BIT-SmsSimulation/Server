/**
 * Implementation of MessageService.
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

package com.chaos.smsSimulation.server.service.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chaos.smsSimulation.server.dao.MessageDao;
import com.chaos.smsSimulation.server.model.Message;
import com.chaos.smsSimulation.server.service.MessageService;
import com.chaos.smsSimulation.server.service.UserService;

/**
 * @author chaos
 *
 */
public class MessageServiceImpl implements MessageService {

	protected static Logger LOGGER = LoggerFactory.getLogger(MessageService.class);
	
	private MessageDao messageDao;
	private UserService userService;

	@Override
	public boolean sendMessage(Message msg) {
		// TODO Auto-generated method stub
		String content = "M\n";
		content += msg.getSenderNum() + "\n";
		content += msg.getReceiverNum() + "\n";
		content += msg.getContent() + "\n";
		content += END_OF_MSG + "\n";

		if (userService.isOnline(msg.getReceiverNum())) {
			if (!send(userService.getUserIp(msg.getReceiverNum()), content)) {
				LOGGER.info("Clear dead user " + msg.getReceiverNum() + ", force logoff");
				userService.forceLogoff(msg.getReceiverNum());
				
				LOGGER.info("Message from " + msg.getSenderNum() + " to " + msg.getReceiverNum() + " failed to be sent");
				LOGGER.info("Saving message to db...");
				return false;
			}
			LOGGER.info("Message from " + msg.getSenderNum() + " to " + msg.getReceiverNum() + " has been sent successfully");
			return true;
		}
		LOGGER.info("Message from " + msg.getSenderNum() + " to " + msg.getReceiverNum() + " failed to be sent");
		LOGGER.info("Saving message to db...");
		return false;
	}
	
	@Override
	public boolean sendMessages(List<Message> msgs) throws Exception {
		// TODO Auto-generated method stub
		Set<String> nums = new HashSet<>();
		for (Message msg : msgs) {
			nums.add(msg.getReceiverNum());
		}
		if (nums.size() == 0) {
			return true;
		} else if (nums.size() != 1) {
			throw new Exception("必须是同一个收件人！");
		}
		
		String content = "";
		for (Message msg : msgs) {
			content += "M\n";
			content += msg.getSenderNum() + "\n";
			content += msg.getReceiverNum() + "\n";
			content += msg.getContent() + "\n";
			content += END_OF_MSG + "\n";
		}
		
		if (msgs.size() > 0 && userService.isOnline(msgs.get(0).getReceiverNum())) {
			if (send(userService.getUserIp(msgs.get(0).getReceiverNum()), content)) {
				for (Message msg : msgs) {
					msg.setStatus(Message.SENT);
					messageDao.update(msg);
					
					sendReceipt(msg, RESULT_DELAY);
				}
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public boolean saveMessage(Message msg) {
		// TODO Auto-generated method stub
		msg.setTime(new Timestamp(System.currentTimeMillis()));
		messageDao.save(msg);
		return true;
	}

	@Override
	public Message getMessageById(String id) {
		// TODO Auto-generated method stub
		return messageDao.getById(id);
	}

	@Override
	public boolean sendUnsentMessages(String receiverNum) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("receiverNum", receiverNum);
		map.put("status", Message.SENDING);
		
		return sendMessages(messageDao.findByMap(map));
	}
	
	@Override
	public boolean sendReceipt(Message msg, int type) {
		// TODO Auto-generated method stub
		String content = "R\n";
		content += msg.getReceiverNum() + " " + String.valueOf(type) + "\n";
		
		if (userService.isOnline(msg.getSenderNum())) {
			if (!send(userService.getUserIp(msg.getSenderNum()), content)) {
				LOGGER.info("Clear dead user " + msg.getSenderNum() + ", force logoff");
				userService.forceLogoff(msg.getSenderNum());
				return false;
			}
			return true;
		}
		return false;
	}
	
	private boolean send(String ip, String content) {
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(ip, SEND_PORT), 5000);
			socket.getOutputStream().write(content.getBytes());
			socket.close();
			return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
