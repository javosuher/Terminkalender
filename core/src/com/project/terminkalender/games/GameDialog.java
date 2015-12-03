package com.project.terminkalender.games;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.tools.DialogActor;
import com.project.terminkalender.userdata.Task;

public class GameDialog extends DialogActor {
	protected TeacherGame game;
	protected TextButton thisButton, actionButton;
	
	public GameDialog(Skin skin, final TeacherGame game, TextButton thisButton) {
		super("", skin);
		this.game = game;
		this.thisButton = thisButton;
	}
	
	protected Array<String> tasksToTaskBox(Array<Task> tasks) {
		Array<String> tasksArray = new Array<String>();
		for(Task task : tasks) {
			String taskArray = task.getName() + " [" + task.getLimit() + "]" + " [" + task.arrayToString(task.getWhatArray()) + "]" + " [" + task.arrayToString(task.getWhereArray()) + "]";
			tasksArray.add(taskArray);
		}
		return tasksArray;
	}
	protected Array<Task> taskBoxToTask(Array<String> tasksArray) {
		Array<Task> tasks = new Array<Task>();
		for(String taskArray : tasksArray) {
			taskArray = taskArray.replace("]", "");
			String [] taskSplit = taskArray.split("\\[");
			for(int index = 0; index < taskSplit.length - 1; ++index) {
				taskSplit[index] = taskSplit[index].substring(0, taskSplit[index].length() - 1);
			}
			String taskName = taskSplit[0];
			String taskLimit = taskSplit[1];
			Array<String> whatArray = new Array<String>();
			if(taskSplit.length > 2 && !taskSplit[2].equals("") && !taskSplit[2].equals(" ")) {
				whatArray = addSecondaryArray(taskSplit[2]);
			}
			Array<String> whereArray = new Array<String>();
			if(taskSplit.length > 3 && !taskSplit[3].equals("") && !taskSplit[3].equals(" ")) {
				whereArray = addSecondaryArray(taskSplit[3]);
			}
			
			Task task = new Task(taskName, taskLimit, whatArray, whereArray);
			tasks.add(task);
		}
		return tasks;
	}
	public static Array<String> addSecondaryArray(String string) {
		Array<String> array = new Array<String>();
		if(string.contains(",")) {
			String [] whatStrings = string.split(",");
			array.addAll(whatStrings);
		}
		else array.add(string);
		return array;
	}
}

