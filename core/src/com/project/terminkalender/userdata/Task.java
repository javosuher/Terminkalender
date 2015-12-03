package com.project.terminkalender.userdata;

import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.websockets.TeacherWebSockets;

public class Task {
	private String name;
	private int limit;
	private Array<String> whatArray, whereArray;

	public Task(String name, int limit, Array<String> what, Array<String> where) {
		this.limit = limit;
		construct(name, what, where);
	}
	public Task(String name, String limit, Array<String> what, Array<String> where) {
		this.limit = Integer.parseInt(limit);
		construct(name, what, where);
		
	}
	private void construct(String name, Array<String> what, Array<String> where) {
		this.name = name;
		this.whatArray = what;
		this.whereArray = where;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setWhatArray(Array<String> whatArray) {
		this.whatArray = whatArray;
	}
	public void setWhereArray(Array<String> whereArray) {
		this.whereArray = whereArray;
	}
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public Array<String> getWhatArray() {
		return whatArray;
	}
	public Array<String> getWhereArray() {
		return whereArray;
	}
	
	@Override
	public String toString() {
		return name + TeacherWebSockets.TASKFIELDPLIT  + limit + TeacherWebSockets.TASKFIELDPLIT  + arrayToString(whatArray) + TeacherWebSockets.TASKFIELDPLIT  + arrayToString(whereArray);
	}
	public String arrayToString(Array<String> array) {
		if(array.size > 0) {
			String string = "";
			for(String newString : array) {
				string += newString + TeacherWebSockets.SPLIT;
			}
			return string.substring(0, string.length() - 1);
		}
		else return "";
	}
}
