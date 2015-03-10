package com.project.terminkalender.calendar;

public class Task {
	private String hour, location, partner;
	
	public Task() {
		erase();
	}
	public Task(String hour, String location, String partner) {
		this.hour = hour;
		this.location = location;
		this.partner = partner;
	}
	
	private void erase() {
		hour = location = partner = "Hollow";
	}
	
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
}
