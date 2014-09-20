/**
 * Interface of message service.
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

import java.util.List;

import com.chaos.smsSimulation.server.model.Message;

/**
 * @author chaos
 *
 */
public interface MessageService {
	
	public static final int SEND_PORT = 3416;
	public static final int RECEIVE_PORT = 3415;
	public static final String END_OF_MSG = "\f";
	
	public static final int RESULT_SUCCESS = 0;
	public static final int RESULT_SENDING = 1;
	public static final int RESULT_DELAY = 2;

	boolean sendMessage(Message msg);
	
	boolean sendMessages(List<Message> msgs) throws Exception;
	
	boolean saveMessage(Message msg);
	
	Message getMessageById(String id);
	
	boolean sendUnsentMessages(String receiverNum) throws Exception;
	
	boolean sendReceipt(Message msg, int type);
}
