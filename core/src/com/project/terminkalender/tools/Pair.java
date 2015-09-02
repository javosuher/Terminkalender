package com.project.terminkalender.tools;

import com.project.terminkalender.websockets.WebSockets;

public class Pair<T> {
	private T firstElement, secondElement;

	public Pair(T firstElement, T secondElement) {
		this.firstElement = firstElement;
		this.secondElement = secondElement;
	}
	
	public void setPair(T first, T second) {
		firstElement = first;
		secondElement = second;
	}
	
	public T getFirst() {
		return firstElement;
	}
	public T getSecond() {
		return secondElement;
	}

	@Override
	public String toString() {
		return firstElement + WebSockets.POINTSPLIT + secondElement;
	}
}
