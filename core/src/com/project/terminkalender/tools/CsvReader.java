package com.project.terminkalender.tools;

import java.io.BufferedReader;
import java.io.IOException;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.websockets.TeacherWebSockets;

public class CsvReader {
	private Array<String> elements;
	
	public CsvReader() {
		elements = new Array<String>();
	}
	
	public Array<String> parse(FileHandle file) throws IOException {
		BufferedReader openFile = new BufferedReader(file.reader("UTF-8"));
		
		String line;
		while((line = openFile.readLine()) != null) {
			String [] splitVector = line.split(TeacherWebSockets.TASKSPLIT);
			for(String element : splitVector) {
				elements.add(element);
			}
		}
		return elements;
	}
	
	public Array<String> getElements() {
		return elements;
	}
}
