/**
 * 
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
