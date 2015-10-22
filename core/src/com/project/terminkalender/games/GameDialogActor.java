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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
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
	public final static String TASK = "Task";
	public final static String USER = "User";
	
	public final static String OPEN = "open";
	public final static String DELETE = "delete";
	
	private final List<String> tasksBox = new List<String>(TeacherMain.skin);
	private final TextField taskNameText = new TextField("", TeacherMain.skin);
	private final TextField taskLimitUsers = new TextField("", TeacherMain.skin);
	private final List<String> usersBox = new List<String>(TeacherMain.skin);
	private final TextField usersText = new TextField("", TeacherMain.skin);
	
	public GameDialogActor(Skin skin, final TeacherGame game, TextButton thisButton) {
		super(skin, game, thisButton);
		
		Table mainParemetersTable = new Table(skin);
		Table taskParametersTable = new Table(skin);
		Table userParametersTable = new Table(skin);
		
		TextButton applyChangesButton = new TextButton("Apply Changes", skin);
		Label essentialDataLabel = new Label("ESSENTIAL DATA", skin);
		Label nameLabel = new Label("Game name: ", skin);
		Label nameGameLabel = new Label(game.getName(), skin);
		Label passwordLabel = new Label("Password: ", skin);
		final TextField passwordText = new TextField(game.getPassword(), skin);
		Label tasksSettingsLabel = new Label("\nTASKS SETTINGS", skin);
		Table taskDataTable = new Table(skin);
		Label newTasksLabel = new Label("Task Name: ", skin);
		Label newTasksNumLabel = new Label("Task Limit:", skin);
		Table taskAddDeleteTable = new Table(skin);
		TextButton addTaskButton = new TextButton("Enter", skin);
		TextButton deleteTaskButton = new TextButton("Delete", skin, "redTextButton");
		ImageButton openTaskFileButton = new ImageButton(skin, "imageButtonFolder");	
		Label userSettingsLabel = new Label("\nUSERS SETTINGS", skin);
		Label newUsersLabel = new Label("New User: ", skin);
		Table userAddDeleteTable = new Table(skin);
		TextButton addUserButton = new TextButton("Shift", skin);
		TextButton deleteUserButton = new TextButton("Delete", skin, "redTextButton");
		ImageButton openUserFileButton = new ImageButton(skin, "imageButtonFolder");
		Table tasksBoxTable = new Table(skin);
		ScrollWindow tasksBoxWindow = new ScrollWindow("TASKS LIST", skin, tasksBoxTable);
		Table usersBoxTable = new Table(skin);
		ScrollWindow usersBoxWindow = new ScrollWindow("USERS LIST", skin, usersBoxTable);
		tasksBox.setItems(game.getTasks());
		actionButton = new TextButton("Open", TeacherMain.skin.get("greenTextButton", TextButtonStyle.class));
		TextButton deleteGameButton = new TextButton("Delete", skin, "redTextButton");
		
		taskDataTable.add(newTasksLabel);
		taskDataTable.add(taskNameText).row();
		taskDataTable.add(newTasksNumLabel).left();
		taskDataTable.add(taskLimitUsers).left().width(50);
		taskAddDeleteTable.add(addTaskButton).width(70).height(35).padBottom(4).row();
		taskAddDeleteTable.add(deleteTaskButton).width(70).height(35);
		tasksBoxTable.add(tasksBox).expand().fill();
		userAddDeleteTable.add(addUserButton).width(70).height(35).padBottom(4).row();
		userAddDeleteTable.add(deleteUserButton).width(70).height(35);
		usersBoxTable.add(usersBox).expand().fill();
		
		getContentTable().padTop(40);
		getButtonTable().defaults().width(150).height(50);
		
		mainParemetersTable.add(essentialDataLabel).colspan(2).row();
		mainParemetersTable.add(nameLabel);
		mainParemetersTable.add(nameGameLabel).row();
		mainParemetersTable.add(passwordLabel);
		mainParemetersTable.add(passwordText).row();
		
		taskParametersTable.add(tasksSettingsLabel).colspan(4).row();
		taskParametersTable.add(taskDataTable);
		taskParametersTable.add(openTaskFileButton).width(50).height(50).padRight(8).padLeft(8);
		taskParametersTable.add(taskAddDeleteTable).row();
		taskParametersTable.add(tasksBoxWindow).padTop(8).colspan(3).width(300).height(200);
		
		userParametersTable.add(userSettingsLabel).colspan(2).row();
		userParametersTable.add(newUsersLabel);
		userParametersTable.add(usersText);
		userParametersTable.add(openUserFileButton).width(50).height(50).padRight(8).padLeft(8);
		userParametersTable.add(userAddDeleteTable).row();
		userParametersTable.add(usersBoxWindow).padTop(8).colspan(3).width(300).height(200);
		
		getContentTable().add(mainParemetersTable).colspan(2).row();
		getContentTable().add(taskParametersTable);
		getContentTable().add(userParametersTable).padLeft(10).row();
		getContentTable().padBottom(10);
		getButtonTable().add(applyChangesButton);
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
		
		addUserButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				addUser();
			} 
		});
		deleteUserButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				removeUser();
			} 
		});
		
		openTaskFileButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				openTasksFile();
			}
		});
		openUserFileButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				openUsersFile();
			}
		});
		
		applyChangesButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				game.setPassword(passwordText.getText());
				game.setTask(tasksBox.getItems());
				game.setUsers(usersBox.getItems());
				game.update();
			}
		});
		
		tasksBox.addListener(new ClickListener(Buttons.RIGHT) {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				removeTask();
			}
		});
		usersBox.addListener(new ClickListener(Buttons.RIGHT) {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				removeUser();
			}
		});
		
		addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Keys.ENTER) {
					addTask();
				}
				if(keycode == Keys.SHIFT_RIGHT) {
					addUser();
				}
				return true;
			}
		});
	}
	
	protected void result(Object object) {
		if(object.equals(OPEN)) {
			game.openGamePetition();
		}
		else if(object.equals(DELETE)) {
			game.removeGamePetition();
		}
	}
	
	private void removeTask() {
		removeInList(tasksBox);
	}
	private void removeUser() {
		removeInList(usersBox);
	}
	private void addTask() {
		String task = taskNameText.getText();
		taskNameText.setText("");
		addInList(task, tasksBox);
	}
	private void addTask(String task) {
		addInList(task, tasksBox);
	}
	private void addUser(String user) {
		addInList(user, usersBox);
	}
	
	private void addUser() {
		String user = usersText.getText();
		usersText.setText("");
		addInList(user, usersBox);
	}
	private void removeInList(List<String> list) {
		String item = list.getSelected();
		deleteItemtInStringList(list, item);
		resizeStringList(list);
	}
	private void addInList(String string, List<String> list) {
		if(!string.equals("")) {
			if(string.contains(TeacherWebSockets.TASKSPLIT) || string.contains(TeacherWebSockets.TASKLIMITSPLIT)) {
				TeacherMain.warningDialog.show("you musn't use ',' and '|'", TeacherMain.teacherGamesScreen.getStage());
			}
			else {
				list.getItems().add(string);
				list.setSelectedIndex(list.getItems().size - 1);
				resizeStringList(list);
			}
		}
	}
	private void resizeStringList(List<String> list) {
		list.validate();
		list.invalidate();
		list.invalidateHierarchy();
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
	
	private void openTasksFile() {
		openFile(TASK, false);
	}
	private void openUsersFile() {
		openFile(USER, true);
	}
	private void openFile(String setting, boolean csv) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		if(csv) {
			FileNameExtensionFilter filterCSV = new FileNameExtensionFilter("CSV Files", "csv");
			fileChooser.setFileFilter(filterCSV);
		}
		FileNameExtensionFilter filterXML = new FileNameExtensionFilter("XML Files", "xml");
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
		    		
		    		if(setting.equals(TASK)) {
		    			Array<Element> newTasks = xmlFile.getChildrenByName("task");
		    			for(Element task : newTasks) {
		    				addTask(task.getText());
		    			}
		    		}
		    		else if(setting.equals(USER)) {
		    			Array<Element> newUsers = xmlFile.getChildrenByName("user");
		    			for(Element user : newUsers) {
		    				addUser(user.getText());
		    			}
		    		}
		    	}
		    	else if(csv && fileExtension.equals(CSV)) {
		    		CsvReader csvTaskReader = new CsvReader();
		    		
		    		if(setting.equals(TASK)) {
		    			Array<String> newTasks = csvTaskReader.parse(Gdx.files.absolute(file.getAbsolutePath()));
			    		for(String task : newTasks) {
			    			addTask(task);
			    		}
		    		}
		    		else if(setting.equals(USER)) {
		    			Array<String> newUsers = csvTaskReader.parse(Gdx.files.absolute(file.getAbsolutePath()));
			    		for(String user : newUsers) {
			    			addTask(user);
			    		}
		    		}
		    	}
		    }
		    catch(Exception exception) { 
		    	TeacherMain.warningDialog.show("Error Loading File", TeacherMain.teacherGamesScreen.getStage());
		    	exception.printStackTrace(); 
		    }
		}
	}
}
