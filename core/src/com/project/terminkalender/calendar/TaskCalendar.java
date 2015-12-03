package com.project.terminkalender.calendar;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.websockets.WebSockets;

public class TaskCalendar {
	public final static String X0 = "Mittwoch";
	public final static String X1 = "Donnerstag";
	public final static String X2 = "Freitag";
	public final static String X3 = "Samstag";
	public final static String X4 = "Sonntag";
	
	public final static String Y0 = "Morgens";
	public final static String Y1 = "Mittags";
	public final static String Y2 = "Nachmittags";
	public final static String Y3 = "Abends";
	
	private Array<String> whatArray, whereArray;
	
	private Slot slot;
	private String description, location, what, where;
	private int numberPartners;
	private Vector2 position;
	private Array<String> partners;
	
	public TaskCalendar() {
		erase();
	}
	public TaskCalendar(String description, int numberPartners, Array<String> whatArray, Array<String> whereArray) {
		this(description, numberPartners, "", "", "");
		this.whatArray = whatArray;
		this.whereArray = whereArray;
	}
	public TaskCalendar(String description, int numberPartners, String location, String what, String where) {
		this.description = description;
		this.numberPartners = numberPartners - 1;
		this.position = new Vector2(-1, -1);
		this.location = location;
		this.what = what;
		this.where = where;
		partners = new Array<String>(numberPartners);
	}
	
	private void erase() {
		description = "";
		numberPartners = 0;
		position = new Vector2(-1, -1);
		location = "";
		partners = new Array<String>();
		what = "";
		where = "";
	}
	
	public void addDataServer() {
		AppMain.webSockets.sendTaskFill(description, location, positionToString(), partnersToString(), what, where);
	}
	
	private String partnersToString() {
		if(partners.size > 0) {
			String partnersString = "";
			for(String partner : partners) {
				partnersString += partner + WebSockets.SPLIT;
			}
			return partnersString.substring(0, partnersString.length() - 1);
		}
		else return "";
	}
	private String positionToString() {
		if(hasPosition()) {
			String x = "", y = "";
			
			if(position.x == 0) x = X0;
			else if(position.x == 1) x = X1;
			else if(position.x == 2) x = X2;
			else if(position.x == 3) x = X3;
			else if(position.x == 4) x = X4;
			
			if(position.y == 0) y = Y0;
			else if(position.y == 1) y = Y1;
			else if(position.y == 2) y = Y2;
			else if(position.y == 3) y = Y3;
			
			return x + WebSockets.SPLIT + y;
		}
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
	public void setPositionCalendar(String x, String y) {
		Vector2 position = new Vector2();
		if(x.equals(X0)) position.x = 0;
		else if(x.equals(X1)) position.x = 1;
		else if(x.equals(X2)) position.x = 2;
		else if(x.equals(X3)) position.x = 3;
		else if(x.equals(X4)) position.x = 4;
		
		if(y.equals(Y0)) position.y = 0;
		else if(y.equals(Y1)) position.y = 1;
		else if(y.equals(Y2)) position.y = 2;
		else if(y.equals(Y3)) position.y = 3;
		
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
	
	public String getWhat() {
		return what;
	}
	public void setWhat(String what) {
		this.what = what;
	}
	
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	
	public Slot getSlot() {
		return slot;
	}
	public void setSlot(Slot slot) {
		this.slot = slot;
	}
	
	public Array<String> getWhatArray() {
		return whatArray;
	}
	public Array<String> getWhereArray() {
		return whereArray;
	}
	
	@Override
	public String toString() {
		return "Task [description=" + description + ", Position=" + position
				+ ", location=" + location + ", NumberPartners=" + numberPartners 
				+ ", what=" + what + ", where=" + where + "]";
	}
}
