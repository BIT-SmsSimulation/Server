/**
 * 
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
	
	boolean login(String num, String ip);
	
	boolean logoff(String num, String ip);
}
