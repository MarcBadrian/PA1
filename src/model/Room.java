/*
 * COMP 6302 - Web Services / Internet
 * PA1: Hotel Reservation System
 * Marc Badrian and Hien Vo - Due 3/5/16
 * 
 */

package model;

public class Room {
	
	private int room_number;
	private String type;
	private double price;
	private int current_occupant;
	
	// getter methods
	public int getRoomNumber() {
		return room_number;
	}
	
	public String getRoomType() {
		return type;
	}
	
	public double getRoomPrice() {
		return price;
	}
	
	public int getCurrentOccupant() {
		return current_occupant;
	}

	// setter methods
	public void setRoomNumber(int room_number) {
		this.room_number = room_number;
	}

	public void setRoomType(String type) {
		this.type = type;
	}
	
	public void setRoomPrice(double price) {
		this.price = price;
	}
	
	public void setOccupant(int current_occupant) {
		this.current_occupant = current_occupant;
	}

}
