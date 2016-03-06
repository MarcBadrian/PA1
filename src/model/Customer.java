/*
 * COMP 6302 - Web Services / Internet
 * PA1: Hotel Reservation System
 * Marc Badrian and Hien Vo - Due 3/5/16
 * 
 */

package model;

public class Customer {

	private int customer_id;
	private String first_name;
	private String last_name;
	private int phone_number;
	private String billing_address;
	private String billing_city;
	private String billing_state;
	private int billing_zip;
	private String checkin_date;
	private String checkout_date;
	
	// getter methods
	public int getId() {
		return customer_id;
	}
	
	public String getFirstName() {
		return first_name;
	}
	
	public String getLastName() {
		return last_name;
	}
	
	public int getNumber() {
		return phone_number;
	}
	
	public String getBillingAddress() {
		return billing_address;
	}
	
	public String getBillingCity() {
		return billing_city;
	}
	
	public String getBillingState() {
		return billing_state;
	}
	
	public int getBillingZip() {
		return billing_zip;
	}
	
	public String getCheckinDate() {
		return checkin_date;
	}
	
	public String getCheckoutDate() {
		return checkout_date;
	}

	// setter methods
	public void setCustomerId(int customer_id) {
		this.customer_id = customer_id;
	}

	public void setName(String first_name, String last_name) {
		this.first_name = first_name;
		this.last_name = last_name;
	}
	
	public void setNumber(int phone_number) {
		this.phone_number = phone_number;
	}
	
	public void setBillingInfo(String billing_address, String billing_city,
	String billing_state, int billing_zip) {
		this.billing_address = billing_address;
		this.billing_city = billing_city;
		this.billing_state = billing_state;
		this.billing_zip = billing_zip;
	}
	
	public void setCheckInOut(String checkin_date, String checkout_date) {
		this.checkin_date = checkin_date;
		this.checkout_date = checkout_date;
	}
	
}
