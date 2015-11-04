package com.project.terminkalender.calendar;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.websockets.WebSockets;

public class TaskCalendar {
	private String description, location;
	private int numberPartners;
	private Vector2 position;
	private Array<String> partners;
	
	public TaskCalendar() {
		erase();
	}
	public TaskCalendar(String description, int numberPartners) {
		this(description, numberPartners, "");
	}
	public TaskCalendar(String description, int numberPartners, String location) {
		this.description = description;
		this.numberPartners = numberPartners - 1;
		this.position = new Vector2(-1, -1);
		this.location = location;
		partners = new Array<String>(numberPartners);
	}
	
	private void erase() {
		description = "";
		numberPartners = 0;
		position = new Vector2(-1, -1);
		location = "";
		partners = new Array<String>();
	}
	
	public void addDataServer() {
		AppMain.webSockets.sendTaskFill(description, location, positionToString(), partnersToString());
	}
	
	private String partnersToString() {
		if(partners.size > 0) {
			String partnersString = "";
			for(String partner : partners) {
				partnersString += partner + WebSockets.TASKSPLIT;
			}
			return partnersString.substring(0, partnersString.length() - 1);
		}
		else return "";
	}
	private String positionToString() {
		if(hasPosition()) return position.x + WebSockets.TASKSPLIT + position.y;
		else return "NoPosition";
	}
	private boolean hasPosition() {
		return position.x != -1 && position.y != -1;
	}
	
	public void addPartner(String partner) {
		partners.add(partner);
	}
	public void clearPartners() {
		partners.clear();
	}
	public Array<String> getPartners() {
		return partners;
	}
	public void setPartners(Array<String> partners) {
		this.partners = partners;
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
	
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public int getNumberPartners() {
		return numberPartners;
	}
	public void setNumberPartners(int numberPartners) {
		this.numberPartners = numberPartners;
	}
	
	@Override
	public String toString() {
		return "Task [description=" + description + ", Position=" + position
				+ ", location=" + location + ", NumberPartners=" + numberPartners + "]";
	}
}
