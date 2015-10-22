package com.project.terminkalender.userdata;

import com.project.terminkalender.websockets.TeacherWebSockets;

public class Task {
	private String name, limit;

	public Task(String name, String limit) {
		this.name = name;
		this.limit = limit;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return name + TeacherWebSockets.TASKLIMITSPLIT  + limit;
	}
}
