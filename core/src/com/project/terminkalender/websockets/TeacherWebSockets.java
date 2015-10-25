package com.project.terminkalender.websockets;

import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Resources;
import com.project.terminkalender.TeacherMain;
import com.project.terminkalender.games.Games;
import com.project.terminkalender.loginandregister.TeacherLoginDialog;

public class TeacherWebSockets extends WebSockets {
	private TeacherLoginDialog teacherLoginDialog;
	private Games games;
	
	public TeacherWebSockets(String serverDirection) {
		super(serverDirection);
	}
	
	@Override
	protected void options(String action, String message) {
		if(action.equals(LOGINTEACHER))
			loginTeacherCheck(message);
		else if(action.equals(REGISTERTEACHER))
			registerTeacherCheck(message);
		else if(action.equals(CREATEGAME))
			createGameCheck(message);
		else if(action.equals(GAMES))
			askGamesCheck(message);
		else if(action.equals(UPDATEGAME))
			updateGameCheck(message);
		else if(action.equals(OPENGAMES))
			openGameProcess(message);
		else if(action.equals(REMOVEGAMES))
			removeGameProcess(message);
		else if(action.equals(CLOSEGAMES))
			closeGameProcess(message);
	}
	
	public void loginTeacherCheck(String message) {
		String userTeacher = message.split(POINTSPLIT)[0];
		String check = message.split(POINTSPLIT)[1];
		
		if(check.equals("NoExist")) {
			Resources.warningDialog.show(userTeacher + " not exists", TeacherMain.teacherLoginRegisterScreen.getStage());
		}
		else if(check.equals("WrongPassword")) {
			Resources.warningDialog.show(userTeacher + " has another password", TeacherMain.teacherLoginRegisterScreen.getStage());
		}
		else {
			teacherLoginDialog.loginSuccess();
		}
	}
	public void registerTeacherCheck(String message) {
		String userTeacher = message.split(POINTSPLIT)[0];
		String check = message.split(POINTSPLIT)[1];
		
		if(check.equals("Failure")) {
			Resources.warningDialog.show(userTeacher + " already exists", TeacherMain.teacherLoginRegisterScreen.getStage());
		}
		else {
			Resources.warningDialog.show(userTeacher + " registered", TeacherMain.teacherLoginRegisterScreen.getStage());
		}
	}
	public void createGameCheck(String message) {
		String gameName = message.split(POINTSPLIT)[0];
		String check = message.split(POINTSPLIT)[1];
		
		if(check.equals("Failure")) {
			Resources.warningDialog.show(gameName + " already exists", TeacherMain.teacherGamesScreen.getStage());
		}
		else {
			Resources.warningDialog.show(gameName + " registered", TeacherMain.teacherGamesScreen.getStage());
		}
	}
	public void askGamesCheck(String message) {
		String [] games = message.split(OPENGAMES);
		Array<String> normalGames = constructArrayGames(games[0]);
		Array<String> openGames = constructArrayGames(games[1].substring(1));
		if(normalGames.size != 0 || openGames.size != 0) {
			this.games.updateGames(normalGames, openGames);
		}
		else this.games.noGames();
	}
	private Array<String> constructArrayGames(String gamesData) {
		String [] games = gamesData.split(POINTSPLIT);
		if(!games[0].equals("")) {
			return new Array<String>(games);
		}
		else return new Array<String>();
	}
	public void updateGameCheck(String message) {
		Resources.warningDialog.show("updated successfully", TeacherMain.teacherGamesScreen.getStage());
	}
	public void openGameProcess(String message) {
		Resources.warningDialog.show(message + " Opened", TeacherMain.teacherGamesScreen.getStage());
	}
	public void removeGameProcess(String message) {
		Resources.warningDialog.show(message + " Deleted", TeacherMain.teacherGamesScreen.getStage());
	}
	public void closeGameProcess(String message) {
		Resources.warningDialog.show(message + " Closed", TeacherMain.teacherGamesScreen.getStage());
	}
	
	public boolean loginTeacher(String teacherUser, String password) {
		return sendMessage(LOGINTEACHER + POINTSPLIT + teacherUser + POINTSPLIT + password);
	}
	public boolean registerTeacher(String teacherUser, String password) {
		return sendMessage(REGISTERTEACHER + POINTSPLIT + teacherUser + POINTSPLIT + password);
	}
	public boolean createGame(String gameName, String teacher, String password) {
		return sendMessage(CREATEGAME + POINTSPLIT + gameName + POINTSPLIT + teacher + POINTSPLIT + password);
	}
	public boolean updateGame(String gameName, String teacher, String password, String tasks, String users) {
		return sendMessage(UPDATEGAME + POINTSPLIT + gameName + POINTSPLIT + teacher + POINTSPLIT + password + POINTSPLIT + tasks + POINTSPLIT + users);
	}
	public boolean askGamesTeacher(String teacher) {
		return sendMessage(GAMES + POINTSPLIT + teacher);
	}
	public boolean openGame(String gameName, String teacher, String password, String tasks, String users) {
		return sendMessage(OPENGAMES + POINTSPLIT + gameName + POINTSPLIT + teacher + POINTSPLIT + password + POINTSPLIT + tasks + POINTSPLIT + users);
	}
	public boolean removeGame(String gameName, String teacher) {
		return sendMessage(REMOVEGAMES + POINTSPLIT + gameName + POINTSPLIT + teacher);
	}
	public boolean closeGame(String gameName, String teacher) {
		return sendMessage(CLOSEGAMES + POINTSPLIT + gameName + POINTSPLIT + teacher);
	}

	public void setTeacherLoginDialog(TeacherLoginDialog teacherLoginDialog) {
		this.teacherLoginDialog = teacherLoginDialog;
	}
	public void setGames(Games games) {
		this.games = games;
	}
}
