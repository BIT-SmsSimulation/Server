/**
 * Interface for user service.
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

package com.chaos.smsSimulation.server.service;

import com.chaos.smsSimulation.server.model.User;

/**
 * @author chaos
 *
 */
public interface UserService {
	
	boolean addUser(User user);

	User getUserById(String id);
	
	User getUserByNum(String num);
	
	boolean hasRecord(String num);
	
	boolean isOnline(String num);
	
	String getUserIp(String num);
	
	boolean login(String num, String ip);
	
	boolean logoff(String num, String ip);
	
	boolean forceLogoff(String num);
}
