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

import java.sql.Timestamp;

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
	public boolean sendUnsentMessages(String receiverNum) {
		// TODO Auto-generated method stub
		return false;
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
