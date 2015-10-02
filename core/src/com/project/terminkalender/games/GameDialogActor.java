package com.project.terminkalender.games;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.project.terminkalender.TeacherMain;
import com.project.terminkalender.tools.CsvReader;
import com.project.terminkalender.tools.ScrollWindow;
import com.project.terminkalender.websockets.TeacherWebSockets;

public class GameDialogActor extends GameDialog {
	public final static String XML = "xml";
	public final static String CSV = "csv";
	
	public final static String OPEN = "open";
	public final static String DELETE = "delete";
	
	private final List<String> tasksBox = new List<String>(TeacherMain.skin);
	private final TextField tasksText = new TextField("", TeacherMain.skin);
	
	public GameDialogActor(Skin skin, final Game game, TextButton thisButton) {
		super(skin, game, thisButton);
		
		Table mainParemetersTable = new Table(skin);
		Table taskParametersTable = new Table(skin);
		
		TextButton applyChangesButton = new TextButton("Apply Changes", skin);
		Label essentialDataLabel = new Label("ESSENTIAL DATA", skin);
		Label nameLabel = new Label("Game name: ", skin);
		Label nameGameLabel = new Label(game.getName(), skin);
		Label passwordLabel = new Label("Password: ", skin);
		final TextField passwordText = new TextField(game.getPassword(), skin);
		Label tasksSettingsLabel = new Label("\nTASKS SETTINGS", skin);
		Label newTasksLabel = new Label("New Task: ", skin);
		Table taskAddDeleteTable = new Table(skin);
		TextButton addTaskButton = new TextButton("Enter", skin);
		TextButton deleteTaskButton = new TextButton("Delete", skin, "redTextButton");
		TextButton openTaskFileButton = new TextButton("Load Tasks from file", skin);
		Table tasksBoxTable = new Table(skin);
		ScrollWindow tasksBoxWindow = new ScrollWindow("TASKS LIST", skin, tasksBoxTable);
		tasksBox.setItems(game.getTasks());
		actionButton = new TextButton("Open", TeacherMain.skin.get("greenTextButton", TextButtonStyle.class));
		TextButton deleteGameButton = new TextButton("Delete", skin, "redTextButton");
		
		getButtonTable().defaults().width(150).height(50);
		
		taskAddDeleteTable.add(addTaskButton).width(70).height(35).padBottom(4).row();
		taskAddDeleteTable.add(deleteTaskButton).width(70).height(35);
		tasksBoxTable.add(tasksBox).expand().fill();
		
		getContentTable().padTop(40);
		mainParemetersTable.add(essentialDataLabel).colspan(2).row();
		mainParemetersTable.add(nameLabel);
		mainParemetersTable.add(nameGameLabel).row();
		mainParemetersTable.add(passwordLabel);
		mainParemetersTable.add(passwordText).row();
		mainParemetersTable.add(tasksSettingsLabel).colspan(2).row();
		mainParemetersTable.add(newTasksLabel);
		mainParemetersTable.add(tasksText).padRight(8);
		mainParemetersTable.add(taskAddDeleteTable).row();
		mainParemetersTable.add(openTaskFileButton).width(220).height(40).colspan(2);
		taskParametersTable.add(tasksBoxWindow).width(300).height(310).row();
		getContentTable().add(mainParemetersTable);
		getContentTable().add(taskParametersTable).padLeft(20).row();
		getContentTable().add(applyChangesButton).colspan(2).width(150).height(50).colspan(2).padTop(10);
		getContentTable().pad(40);
		
		button(actionButton, OPEN);
		button(deleteGameButton, DELETE);
		
		addTaskButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				addTask();
			} 
		});
		deleteTaskButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				removeTask();
			} 
		});
		applyChangesButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				game.setPassword(passwordText.getText());
				game.setTask(tasksBox.getItems());
				game.update();
			}
		});
		openTaskFileButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				openTaskFile();
			}
		});
		
		tasksBox.addListener(new ClickListener(Buttons.RIGHT) {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				removeTask();
			}
		});
		
		addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Keys.ENTER) {
					addTask();
				}
				return true;
			}
		});
	}
	
	private void removeTask() {
		String task = tasksBox.getSelected();
		deleteItemtInStringList(tasksBox, task);
		resizeTaskBox();
	}
	private void addTask() {
		String task = tasksText.getText().toLowerCase();
		tasksText.setText("");
		addTask(task);
	}
	private void addTask(String task) {
		if(!task.equals("")) {
			if(task.contains(TeacherWebSockets.TASKSPLIT)) {
				TeacherMain.warningDialog.show("you musn't use ','", TeacherMain.teacherGamesScreen.getStage());
			}
			else tasksBox.getItems().add(task);
		}
		tasksBox.setSelectedIndex(tasksBox.getItems().size - 1);
		resizeTaskBox();
	}
	private void resizeTaskBox() {
		tasksBox.validate();
		tasksBox.invalidate();
		tasksBox.invalidateHierarchy();
	}
	private void deleteItemtInStringList(List<String> list, String item) {
		int taskIndex = list.getSelectedIndex();
		list.getItems().removeValue(item, false);
		if(taskIndex - 1 >= 0) {
			list.setSelectedIndex(taskIndex - 1); 
		}
		else if(list.getItems().size > 0) {
			list.setSelectedIndex(0); 
		}
	}
	
	private void openTaskFile() {
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filterXML = new FileNameExtensionFilter("XML Files", "xml");
		FileNameExtensionFilter filterCSV = new FileNameExtensionFilter("CSV Files", "csv");
		fileChooser.setFileFilter(filterCSV);
		fileChooser.setFileFilter(filterXML);
		
		int result = fileChooser.showOpenDialog(new JFrame());
		if(result == JFileChooser.APPROVE_OPTION) {
		    File file = fileChooser.getSelectedFile();
		    
		    String fileName = file.getName();
		    String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		    
		    try {
		    	if(fileExtension.equals(XML)) {
		    		XmlReader xmlReader = new XmlReader();
		    		Element xmlFile = xmlReader.parse(Gdx.files.absolute(file.getAbsolutePath()));
		    		Array<Element> tasksElement = xmlFile.getChildrenByName("task");
		    		for(Element taskElement : tasksElement) {
		    			addTask(taskElement.getText());
		    		}
		    	}
		    	else if(fileExtension.equals(CSV)) {
		    		CsvReader csvTaskReader = new CsvReader();
		    		Array<String> newTasks = csvTaskReader.parse(Gdx.files.absolute(file.getAbsolutePath()));
		    		for(String task : newTasks) {
		    			addTask(task);
		    		}
		    	}
		    }
		    catch(IOException exception) { 
		    	TeacherMain.warningDialog.show("Error", TeacherMain.teacherGamesScreen.getStage());
		    	exception.printStackTrace(); 
		    }
		}
	}
	
	protected void result(Object object) {
		if(object.equals(OPEN)) {
			game.openGamePetition();
		}
		else if(object.equals(DELETE)) {
			game.removeGamePetition();
		}
	}
}
