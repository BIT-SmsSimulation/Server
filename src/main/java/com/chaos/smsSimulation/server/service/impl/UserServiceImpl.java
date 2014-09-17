/**
 * 
 */
package com.chaos.smsSimulation.server.service.impl;

import java.util.List;

import com.chaos.smsSimulation.server.dao.UserDao;
import com.chaos.smsSimulation.server.model.User;
import com.chaos.smsSimulation.server.service.MessageService;
import com.chaos.smsSimulation.server.service.UserService;

/**
 * @author chaos
 *
 */
public class UserServiceImpl implements UserService {

	private UserDao userDao;
	private MessageService messageService;
	
	@Override
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		user.setStatus(User.ONLINE);
		userDao.save(user);
		return true;
	}

	@Override
	public User getUserById(String id) {
		// TODO Auto-generated method stub
		return userDao.getById(id);
	}

	@Override
	public User getUserByNum(String num) {
		// TODO Auto-generated method stub
		List<User> users = userDao.findByProperty("contact", num);
		
		if (users.size() == 0) {
			return null;
		} else {
			return users.get(0);
		}
	}

	@Override
	public boolean hasRecord(String num) {
		// TODO Auto-generated method stub
		return userDao.findByProperty("contact", num).size() != 0;
	}

	@Override
	public boolean isOnline(String num) {
		// TODO Auto-generated method stub
		List<User> users = userDao.findByProperty("contact", num);
		if (users.size() == 0 || users.get(0).getStatus() == User.OFFLINE) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean login(String num, String ip) {
		// TODO Auto-generated method stub
		List<User> users = userDao.findByProperty("contact", num);
		if (users .size() == 0) {
			User user = new User();
			user.setContact(num);
			user.setIp(ip);
			return addUser(user);
		} else if (users.get(0).getStatus() == User.ONLINE) {
			return false;
		} else {
			users.get(0).setStatus(User.ONLINE);
			userDao.update(users.get(0));
			return true;
		}
	}

	@Override
	public boolean logoff(String num, String ip) {
		// TODO Auto-generated method stub
		List<User> users = userDao.findByProperty("contact", num);
		if (users .size() == 0) {
			return false;
		} else if (!users.get(0).getIp().equals(ip)) {
			return false;
		} else {
			users.get(0).setStatus(User.OFFLINE);
			userDao.update(users.get(0));
			return true;
		}
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
}
