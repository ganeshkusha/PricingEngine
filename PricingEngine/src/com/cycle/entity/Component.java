package com.cycle.entity;

/**
 * This pojo is for Cycle higher components
 */

public class Component {

	private String frame;
	private String handleBarWithBrakes;
	private String seating;
	private String wheels;
	private String chainAssembly;
	private int bookingYear;
	private int bookingId;

	public String getFrame() {
		return frame;
	}

	public void setFrame(String frame) {
		this.frame = frame;
	}

	public String getHandleBarWithBrakes() {
		return handleBarWithBrakes;
	}

	public void setHandleBarWithBrakes(String handleBarWithBrakes) {
		this.handleBarWithBrakes = handleBarWithBrakes;
	}

	public String getSeating() {
		return seating;
	}

	public void setSeating(String seating) {
		this.seating = seating;
	}

	public String getWheels() {
		return wheels;
	}

	public void setWheels(String wheels) {
		this.wheels = wheels;
	}

	public String getChainAssembly() {
		return chainAssembly;
	}

	public void setChainAssembly(String chainAssembly) {
		this.chainAssembly = chainAssembly;
	}

	public int getBookingYear() {
		return bookingYear;
	}

	public void setBookingYear(int bookingYear) {
		this.bookingYear = bookingYear;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

}
