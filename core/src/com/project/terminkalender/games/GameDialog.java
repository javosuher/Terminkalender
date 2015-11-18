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
			String taskArray = task.getName() + " [" + task.getLimit() + "]";
			tasksArray.add(taskArray);
		}
		return tasksArray;
	}
	protected Array<Task> TaskBoxToTask(Array<String> tasksArray) {
		Array<Task> tasks = new Array<Task>();
		for(String taskArray : tasksArray) {
			String taskLimit = taskArray.substring(taskArray.length() - 2, taskArray.length() - 1);
			String taskName = taskArray.substring(0, taskArray.length() - 4);
			Task task = new Task(taskName, taskLimit);
			tasks.add(task);
		}
		return tasks;
	}
}

