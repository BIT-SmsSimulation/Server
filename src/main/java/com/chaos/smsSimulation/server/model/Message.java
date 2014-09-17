/**
 * 
 */
package com.chaos.smsSimulation.server.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author chaos
 *
 */
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2055319858734292987L;
	
	public static final int SENT = 0;
	public static final int SENDING = 1;
	
	private String id;
	private String content;
	private String senderNum;
	private String receiverNum;
	private Timestamp time;
	private Integer status;
	
	private User sender;
	private User receiver;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSenderNum() {
		return senderNum;
	}
	public void setSenderNum(String senderNum) {
		this.senderNum = senderNum;
	}
	public String getReceiverNum() {
		return receiverNum;
	}
	public void setReceiverNum(String receiverNum) {
		this.receiverNum = receiverNum;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
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
