package com.project.terminkalender.calendar;

public class TaskCalendar {
	private String description, position, location, partner;
	
	public TaskCalendar() {
		erase();
	}
	public TaskCalendar(String description) {
		this.description = description;
		this.position = "";
		this.location = "";
		this.partner = "";
	}
	public TaskCalendar(String description, String position, String location, String partner) {
		this.description = description;
		this.position = position;
		this.location = location;
		this.partner = partner;
	}
	
	private void erase() {
		description = "";
		position = "";
		location = "";
		partner = "";
	}
	
	public String getDescription() {
		return description;
	}
	public String getShortDescription() {
		if(description.length() > 7) {
			return description.substring(0, 6) + "..";
		}
		else return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String hour) {
		this.position = hour;
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
		return "Task [description=" + description + ", Position=" + position
				+ ", location=" + location + ", partner=" + partner + "]";
	}
}
