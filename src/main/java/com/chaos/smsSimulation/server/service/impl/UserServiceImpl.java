/**
 * Implementation of UserService.
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chaos.smsSimulation.server.dao.UserDao;
import com.chaos.smsSimulation.server.model.User;
import com.chaos.smsSimulation.server.service.MessageService;
import com.chaos.smsSimulation.server.service.UserService;

/**
 * @author chaos
 *
 */
public class UserServiceImpl implements UserService {
	
	protected static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	private UserDao userDao;
	private MessageService messageService;
	
	@Override
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		LOGGER.info("New user " + user.getContact() + " comes @" + user.getIp());
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
	public String getUserIp(String num) {
		// TODO Auto-generated method stub
		List<User> users = userDao.findByProperty("contact", num);
		if (users.size() == 0) {
			return null;
		} else {
			return users.get(0).getIp();
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
			if (users.get(0).getIp().equals(ip)) {
				LOGGER.info(num + "@" + ip + " login again, ignore");
				return false;
			} else {
				LOGGER.info("User@" + ip + " tried to login " + num + "@" + users.get(0).getIp() + ", operation refused");
				return false;
			}
		} else {
			User user = users.get(0);
			
			Map<String, Object> map = new HashMap<>();
			map.put("ip", ip);
			map.put("status", User.ONLINE);
			users = userDao.findByMap(map);
			if (users.size() != 0) {
				for (User deadUser : users) {
					LOGGER.info("Clear dead user " + deadUser.getContact() + ", force logoff");
					deadUser.setStatus(User.OFFLINE);
					userDao.update(deadUser);
				}
			}
			
			LOGGER.info(num + "@" + ip + ": login success");
			user.setIp(ip);
			user.setStatus(User.ONLINE);
			userDao.update(user);
			return true;
		}
	}

	@Override
	public boolean logoff(String num, String ip) {
		// TODO Auto-generated method stub
		List<User> users = userDao.findByProperty("contact", num);
		if (users .size() == 0) {
			LOGGER.info("User@" + ip + " tried to logoff nonexistent user " + num + ", operation refused");
			return false;
		} else if (!users.get(0).getIp().equals(ip)) {
			LOGGER.info("User@" + ip + " tried to logoff " + num + "@" + users.get(0).getIp() + ", operation refused");
			return false;
		} else {
			LOGGER.info(num + "@" + ip + ": logoff success");
			users.get(0).setStatus(User.OFFLINE);
			userDao.update(users.get(0));
			return true;
		}
	}
	
	@Override
	public boolean forceLogoff(String num) {
		// TODO Auto-generated method stub
		List<User> users = userDao.findByProperty("contact", num);
		if (users.size() == 0) {
			return false;
		}
		
		users.get(0).setStatus(User.OFFLINE);
		userDao.update(users.get(0));
		return true;
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
