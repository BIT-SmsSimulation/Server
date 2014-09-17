/**
 * Model of user.
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

package com.chaos.smsSimulation.server.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author chaos
 *
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 236673941168392358L;

	public static final int OFFLINE = 0;
	public static final int ONLINE = 1;
	
	private String id;
	private String contact;
	private String ip;
	private Integer status;
	
	private List<Message> messages;
	
//	private String backupField1;
//	private String backupField2;
//	private String backupField3;
//	private String backupField4;
//	private String backupField5;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
//	public String getBackupField1() {
//		return backupField1;
//	}
//	public void setBackupField1(String backupField1) {
//		this.backupField1 = backupField1;
//	}
//	public String getBackupField2() {
//		return backupField2;
//	}
//	public void setBackupField2(String backupField2) {
//		this.backupField2 = backupField2;
//	}
//	public String getBackupField3() {
//		return backupField3;
//	}
//	public void setBackupField3(String backupField3) {
//		this.backupField3 = backupField3;
//	}
//	public String getBackupField4() {
//		return backupField4;
//	}
//	public void setBackupField4(String backupField4) {
//		this.backupField4 = backupField4;
//	}
//	public String getBackupField5() {
//		return backupField5;
//	}
//	public void setBackupField5(String backupField5) {
//		this.backupField5 = backupField5;
//	}
}
