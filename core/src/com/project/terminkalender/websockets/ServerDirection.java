package com.project.terminkalender.websockets;

public final class ServerDirection {
	public static final int PORT = 8080;
	public static final String IP = "192.168.1.133";
	public static final String serverDirection = "ws://"+ IP +":"+ PORT;
	
	@Override
	public String toString() {
		return serverDirection;
	}
}
