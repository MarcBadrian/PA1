/*
 * COMP 6302 - Web Services / Internet
 * PA1: Hotel Reservation System
 * Marc Badrian and Hien Vo - Due 3/5/16
 * 
 */

package model;

public class Transaction {
	
	private int id;
	private int customer_id;
	private int room_number;
	private double amount;
	private int cc_number;
	private int expiration_date;
	
	// getter methods
	public int getId() {
		return id;
	}
	
	public int getCustomerId() {
		return customer_id;
	}
	
	public int getRoomNumber() {
		return room_number;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public int getCCNumber() {
		return cc_number;
	}
	
	public int getExpDate() {
		return expiration_date;
	}

	// setter methods
	public void setId(int id) {
		this.id = id;
	}
	
	public void setCustId(int customer_id) {
		this.customer_id = customer_id;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setCCNum(int cc_number) {
		this.cc_number = cc_number;
	}
	
	public void setExpDate(int expiration_date) {
		this.expiration_date = expiration_date;
	}

}