/**
 * 
 */
package com.chaos.smsSimulation.server.service;

import com.chaos.smsSimulation.server.model.Message;

/**
 * @author chaos
 *
 */
public interface MessageService {
	
	public static int SEND_PORT = 3416;
	public static int RECEIVE_PORT = 3415;

	boolean sendMessage(Message msg);
	
	boolean saveMessage(Message msg);
	
	Message getMessageById(String id);
	
	boolean sendUnsentMessages(String receiverNum);
}
