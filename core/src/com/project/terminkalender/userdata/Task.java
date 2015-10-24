package com.project.terminkalender.userdata;

import com.project.terminkalender.websockets.TeacherWebSockets;

public class Task {
	private String name;
	private int limit;

	public Task(String name, int limit) {
		this.name = name;
		this.limit = limit;
	}
	public Task(String name, String limit) {
		this.name = name;
		this.limit = Integer.parseInt(limit);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return name + TeacherWebSockets.TASKLIMITSPLIT  + limit;
	}
}
