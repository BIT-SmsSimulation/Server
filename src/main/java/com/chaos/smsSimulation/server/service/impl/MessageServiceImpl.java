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
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chaos.smsSimulation.server.dao.MessageDao;
import com.chaos.smsSimulation.server.model.Message;
import com.chaos.smsSimulation.server.service.MessageService;
import com.chaos.smsSimulation.server.service.UserService;

/**
 * @author chaos
 *
 */
public class MessageServiceImpl implements MessageService {
	
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
			return send(userService.getUserIp(msg.getReceiverNum()), content);
		}
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
			return send(userService.getUserIp(msgs.get(0).getReceiverNum()), content);
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
	
	private boolean send(String ip, String content) {
		
		try {
			Socket socket = new Socket(ip, SEND_PORT);
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
