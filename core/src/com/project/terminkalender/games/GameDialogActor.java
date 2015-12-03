package com.project.terminkalender.games;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
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
import com.project.terminkalender.Resources;
import com.project.terminkalender.TeacherMain;
import com.project.terminkalender.tools.CsvReader;
import com.project.terminkalender.tools.DialogActor;
import com.project.terminkalender.tools.ScrollWindow;
import com.project.terminkalender.userdata.Task;
import com.project.terminkalender.websockets.TeacherWebSockets;
import com.project.terminkalender.websockets.WebSockets;

public class GameDialogActor extends GameDialog {
	public final static String XML = "xml";
	public final static String CSV = "csv";
	public final static String TASK = "Task";
	public final static String USER = "User";
	
	public final static String OPEN = "open";
	public final static String DELETE = "delete";
	
	private final DialogActor tasksDialog = new DialogActor("", Resources.skin);
	private final List<String> tasksBox = new List<String>(Resources.skin);
	private final List<String> tasksBoxDialog = new List<String>(Resources.skin);
	private final TextField taskNameText = new TextField("", Resources.skin);
	SelectBox<String> tasksLimitUserSelect = new SelectBox<String>(Resources.skin);
	private final TextField taskWhatText = new TextField("", Resources.skin);
	private final TextField taskWhereText = new TextField("", Resources.skin);
	private final List<String> usersBox = new List<String>(Resources.skin);
	private final TextField usersText = new TextField("", Resources.skin);
	
	public GameDialogActor(Skin skin, final TeacherGame game, TextButton thisButton) {
		super(skin, game, thisButton);
		
		Table mainParemetersTable = new Table(skin);
		Table taskParametersTable = new Table(skin);
		Table userParametersTable = new Table(skin);
		
		TextButton applyChangesButton = new TextButton("Apply Changes", skin, "textButtonLarge");
		Label essentialDataLabel = new Label("ESSENTIAL DATA", skin);
		Label nameLabel = new Label("Game name: ", skin);
		Label nameGameLabel = new Label(game.getName(), skin);
		CheckBox showPasswordCheckBox = new CheckBox("Show", skin);
		Label passwordLabel = new Label("Password: ", skin);
		final TextField passwordText = new TextField(game.getPassword(), skin);
		Label tasksSettingsLabel = new Label("\nTASKS SETTINGS", skin);
		Table taskDataTable = new Table(skin);
		TextButton configTasksButton = new TextButton("Config Tasks", skin);
		TextButton tasksAccept = new TextButton("OK", skin, "textButtonLarge");
		Label primaryFieldsLabel = new Label("PRIMARY FIELDS", skin);
		Label newTasksLabel = new Label("Task Name: ", skin);
		Label newTasksNumLabel = new Label("Task Limit:", skin);
		Label secondaryFieldsLabel = new Label("SECONDARY FIELDS", skin);
		Label newTaskWhatLabel = new Label("What: ", skin);
		Label newTaskWhereLabel = new Label("Where: ", skin);
		Label tasksExplanationLabel = new Label("Secondary fields can be empty. Their structure is formed by words split with commas. eg: 'word,word,word'", skin);
		Table taskAddDeleteTable = new Table(skin);
		TextButton addTaskButton = new TextButton("Enter", skin);
		TextButton deleteTaskButton = new TextButton("Delete", skin, "redTextButton");
		ImageButton openTaskFileButton = new ImageButton(skin, "imageButtonFolder");	
		Label userSettingsLabel = new Label("\nUSERS SETTINGS", skin);
		Label newUsersLabel = new Label("New User: ", skin);
		Table userAddDeleteTable = new Table(skin);
		TextButton addUserButton = new TextButton("Enter", skin);
		TextButton deleteUserButton = new TextButton("Delete", skin, "redTextButton");
		ImageButton openUserFileButton = new ImageButton(skin, "imageButtonFolder");
		Table tasksBoxTable = new Table(skin);
		Table tasksBoxTableDialog = new Table(skin);
		ScrollWindow tasksBoxWindow = new ScrollWindow("TASKS LIST", skin, tasksBoxTable);
		ScrollWindow tasksBoxWindowDialog = new ScrollWindow("TASKS LIST", skin, tasksBoxTableDialog);
		Table usersBoxTable = new Table(skin);
		ScrollWindow usersBoxWindow = new ScrollWindow("USERS LIST", skin, usersBoxTable);
		tasksBox.setItems(tasksToTaskBox(game.getTasks()));
		usersBox.setItems(game.getUsers());
		actionButton = new TextButton("Open", Resources.skin.get("textButtonLargeGreen", TextButtonStyle.class));
		TextButton deleteGameButton = new TextButton("Delete", skin, "textButtonLargeRed");
		
		Array<String> tasksLimitRange = new Array<String>();
		tasksLimitRange.add("1");
		tasksLimitRange.add("2");
		tasksLimitRange.add("3");
		tasksLimitRange.add("4");
		tasksLimitUserSelect.setItems(tasksLimitRange);
		tasksLimitUserSelect.setSelectedIndex(1);
		tasksExplanationLabel.setWrap(true);
		
		taskDataTable.add(primaryFieldsLabel).colspan(2).row();
		taskDataTable.add(newTasksLabel);
		taskDataTable.add(taskNameText).row();
		taskDataTable.add(newTasksNumLabel);
		taskDataTable.add(tasksLimitUserSelect).width(50).padBottom(20).row();
		taskDataTable.add(secondaryFieldsLabel).colspan(2).row();
		taskDataTable.add(newTaskWhatLabel);
		taskDataTable.add(taskWhatText).row();
		taskDataTable.add(newTaskWhereLabel);
		taskDataTable.add(taskWhereText).row();
		taskDataTable.add(tasksExplanationLabel).colspan(2).width(300).height(10).padTop(50);
		taskAddDeleteTable.add(addTaskButton).width(70).height(35).padBottom(4).row();
		taskAddDeleteTable.add(deleteTaskButton).width(70).height(35);
		tasksBoxTable.add(tasksBox).expand().fill();
		userAddDeleteTable.add(addUserButton).width(70).height(35).padBottom(4).row();
		userAddDeleteTable.add(deleteUserButton).width(70).height(35);
		usersBoxTable.add(usersBox).expand().fill();
		tasksBoxTableDialog.add(tasksBoxDialog).expand().fill();
		
		getContentTable().padTop(40);
		getButtonTable().defaults().width(185).height(60);
		
		mainParemetersTable.add(essentialDataLabel).colspan(3).row();
		mainParemetersTable.add(nameLabel);
		mainParemetersTable.add(nameGameLabel).row();
		mainParemetersTable.add(passwordLabel);
		mainParemetersTable.add(passwordText).padRight(8);
		mainParemetersTable.add(showPasswordCheckBox).row();
		
		taskParametersTable.add(tasksSettingsLabel).colspan(2).row();
		taskParametersTable.add(configTasksButton).width(125).height(50).padTop(12);
		tasksDialog.getContentTable().padTop(40);
		tasksDialog.getButtonTable().defaults().width(175).height(100);
		tasksDialog.getContentTable().add(taskDataTable);
		tasksDialog.getContentTable().add(taskAddDeleteTable);
		tasksDialog.getContentTable().add(tasksBoxWindowDialog).padLeft(20).padTop(8).colspan(3).width(400).height(300).row();
		tasksDialog.getContentTable().padBottom(20);
		tasksDialog.button(tasksAccept);
		taskParametersTable.add(openTaskFileButton).width(50).height(50).padRight(8).padTop(12).row();
		taskParametersTable.add(tasksBoxWindow).padLeft(30).padTop(20).colspan(3).width(300).height(200);
		
		userParametersTable.add(userSettingsLabel).colspan(4).row();
		userParametersTable.add(newUsersLabel);
		userParametersTable.add(usersText);
		userParametersTable.add(openUserFileButton).width(50).height(50).padRight(8).padLeft(8);
		userParametersTable.add(userAddDeleteTable).row();
		userParametersTable.add(usersBoxWindow).padTop(8).padLeft(40).colspan(3).width(300).height(200);
		
		getContentTable().add(mainParemetersTable).colspan(2).row();
		getContentTable().add(taskParametersTable);
		getContentTable().add(userParametersTable).padLeft(10).row();
		getContentTable().padBottom(10);
		getButtonTable().add(applyChangesButton);
		button(actionButton, OPEN);
		button(deleteGameButton, DELETE);
		
		configTasksButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				tasksBoxDialog.setItems(tasksBox.getItems());
				tasksDialog.show(getStage());
			} 
		});
		tasksAccept.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				tasksBox.setItems(tasksBoxDialog.getItems());
			} 
		});
		
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
				Array<Task> tasks = taskBoxToTask(tasksBox.getItems());
				Array<String> users = usersBox.getItems();
				
				if(isTaskRepeat(tasks)) {
					Resources.warningDialog.show("You mustn't have two or more tasks with the same name", TeacherMain.teacherGamesScreen.getStage());
				}
				else if(isUserRepeat(users)) {
					Resources.warningDialog.show("You mustn't have two or more users with the same name", TeacherMain.teacherGamesScreen.getStage());
				}
				else {
					game.setPassword(passwordText.getText());
					game.setTasks(tasks);
					game.setUsers(users);
					game.update();
				}
			}
		});
		
		tasksBoxDialog.addListener(new ClickListener(Buttons.RIGHT) {

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
					addUser();
				}
				return true;
			}
		});
		tasksDialog.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Keys.ENTER) {
					addTask();
				}
				return true;
			}
		});
		
		passwordText.setPasswordCharacter('*');
		passwordText.setPasswordMode(true);
		showPasswordCheckBox.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				passwordText.setPasswordMode(!passwordText.isPasswordMode());
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
		removeInList(tasksBoxDialog);
	}
	private void removeUser() {
		removeInList(usersBox);
	}
	private void addTask() {
		String taskName = taskNameText.getText();
		taskNameText.setText("");
		addTask(taskName, tasksLimitUserSelect.getSelected(), taskWhatText.getText(), taskWhereText.getText(), tasksBoxDialog);
	}
	private void addTask(String taskName, String taskLimit, String whatString, String whereString, List<String> list) {
		if(!taskName.equals("") && validateFieldString(taskName)) {
			boolean add = true;
			String task = taskName + " [" + taskLimit + "]";
			if(validateTaskSecondaryFieldString(whatString)) {
				task = task + " [" + whatString + "]";
			}
			else add = false;
			taskWhatText.setText("");
			if(validateTaskSecondaryFieldString(whereString)) {
				task = task + " [" + whereString + "]";
			}
			else add = false;
			taskWhereText.setText("");
			if(add) {
				addInList(task, list);
			}
		}
	}
	private boolean validateFieldString(String string) {
		if(string.contains(TeacherWebSockets.TASKSPLIT) || string.contains(TeacherWebSockets.TASKFIELDPLIT) || 
			string.contains(TeacherWebSockets.DATASPLIT) || string.contains(TeacherWebSockets.POINTSPLIT) || string.contains(TeacherWebSockets.SPLIT)) {
			Resources.warningDialog.show("you musn't use '/', ',', ';', ':' or '-'", TeacherMain.teacherGamesScreen.getStage());
			return false;
		}
		else return true;
	}
	private boolean validateTaskSecondaryFieldString(String string) {
		if(string.contains(TeacherWebSockets.TASKFIELDPLIT) || string.contains(TeacherWebSockets.DATASPLIT) || 
		   string.contains(TeacherWebSockets.POINTSPLIT) || string.contains(TeacherWebSockets.TASKSPLIT)) {
			Resources.warningDialog.show("you musn't use '/', ';', ':' or '-' in secondary fields", TeacherMain.teacherGamesScreen.getStage());
			return false;
		}
		else return true;
	}
	private void addUser(String user) {
		addInListValidate(user, usersBox);
	}
	
	private void addUser() {
		String user = usersText.getText();
		usersText.setText("");
		addInListValidate(user, usersBox);
	}
	private void removeInList(List<String> list) {
		String item = list.getSelected();
		deleteItemtInStringList(list, item);
		resizeStringList(list);
	}
	private void addInListValidate(String string, List<String> list) {
		if(validateFieldString(string)) {
			addInList(string, list);
		}
	}
	private void addInList(String string, List<String> list) {
		list.getItems().add(string);
		list.setSelectedIndex(list.getItems().size - 1);
		resizeStringList(list);
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
	
	private boolean isTaskRepeat(Array<Task> tasks) {
		int index = 0;
		for(Task task : tasks) {
			++index;
			int indexIteration = index;
			while(indexIteration < tasks.size) {
				if(task.getName().toLowerCase().equals(tasks.get(indexIteration).getName().toLowerCase())) {
					return true;
				}
				++indexIteration;
			}
		}
		return false;
	}
	private boolean isUserRepeat(Array<String> users) {
		int index = 0;
		for(String user : users) {
			++index;
			int indexIteration = index;
			while(indexIteration < users.size) {
				if(user.toLowerCase().equals(users.get(indexIteration).toLowerCase())) {
					return true;
				}
				++indexIteration;
			}
		}
		return false;
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
		    				String what = secondaryField(task, "what");
		    				String where = secondaryField(task, "where");
		    				if(inTaskLimitRange(task.get("limit"))) {
		    					addTask(task.get("name"), task.get("limit"), what, where, tasksBox);
		    				}
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
		    		
		    		if(setting.equals(USER)) {
		    			Array<String> newUsers = csvTaskReader.parse(Gdx.files.absolute(file.getAbsolutePath()));
			    		for(String user : newUsers) {
			    			addUser(user);
			    		}
		    		}
		    	}
		    }
		    catch(Exception exception) { 
		    	Resources.warningDialog.show("Error Loading File", TeacherMain.teacherGamesScreen.getStage());
		    	exception.printStackTrace(); 
		    }
		}
	}
	
	private boolean inTaskLimitRange(String taskLimit) {
		return taskLimit.equals("1") || taskLimit.equals("2") || taskLimit.equals("3") || taskLimit.equals("4");
	}
	private String secondaryField(Element task, String field) {
		String string = "";
		Element elementFields = task.getChildByName(field);
		if(elementFields != null) {
			Array<Element> stringArray = elementFields.getChildrenByName("field");
			for(Element element : stringArray) {
				string = string + element.getText() + WebSockets.SPLIT;
			}
			if(!string.equals("")) {
				string = string.substring(0, string.length() - 1);
			}
		}
		return string;
	}
}
