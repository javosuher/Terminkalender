package com.project.terminkalender.calendar;

public class Task {
	private String description, hour, location, partner;
	
	public Task() {
		erase();
	}
	public Task(String description) {
		this.description = description;
		this.hour = "Hollow";
		this.location = "Hollow";
		this.partner = "Hollow";
	}
	public Task(String description, String hour, String location, String partner) {
		this.description = description;
		this.hour = hour;
		this.location = location;
		this.partner = partner;
	}
	
	private void erase() {
		description = "";
		hour = "Hollow";
		location = "Hollow";
		partner = "Hollow";
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	
	@Override
	public String toString() {
		return "Task [description=" + description + ", hour=" + hour
				+ ", location=" + location + ", partner=" + partner + "]";
	}
}
