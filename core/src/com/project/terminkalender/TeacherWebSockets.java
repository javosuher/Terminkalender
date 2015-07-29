package com.project.terminkalender;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.games.Games;
import com.project.terminkalender.loginandregister.TeacherLoginDialog;

public class TeacherWebSockets {
	public final static String POINTSPLIT = ":";
	public final static String DATASPLIT = ";";
	public final static String TASKSPLIT = ",";
	public final static String LOGINTEACHER = "LoginTeacher";
	public final static String REGISTERTEACHER = "RegisterTeacher";
	public final static String CREATEGAME = "CreateGame";
	public final static String UPDATEGAME = "UpdateGame";
	public final static String GAMES = "Games";
	public final static String OPENGAME = "OpenGame";
	
	private WebSocketClient wsc;
	private boolean connected;
	
	private TeacherLoginDialog teacherLoginDialog;
	private Games games;
	
	public TeacherWebSockets(String serverDirection) {
		connect(serverDirection);
	}
	
	public void connect(String serverDirection) {
		URI url = null;
		try {
			url = new URI(serverDirection);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} 
		
		Draft standard = new Draft_17();
		wsc = new WebSocketClient(url, standard) {

			@Override
			public void onOpen(ServerHandshake handshake) {
				Gdx.app.log("WebSocket", "WSClient Conected.");
				connected = true;
			}

			@Override
			public void onMessage(String message) {
				Gdx.app.log("WebSocket", "WSClient receive message -> " + message);
				
				String action = message.split(POINTSPLIT)[0];
				String trueMessage = message.substring(action.length() + 1);
				
				if(action.equals(LOGINTEACHER))
					loginTeacherCheck(trueMessage);
				else if(action.equals(REGISTERTEACHER))
					registerTeacherCheck(trueMessage);
				else if(action.equals(CREATEGAME))
					createGameCheck(trueMessage);
				else if(action.equals(GAMES))
					askGamesCheck(trueMessage);
					
			}

			@Override
			public void onError(Exception ex) {
				Gdx.app.log("WebSocket", "WSClient Error -> " + ex);
			}

			@Override
			public void onClose(int code, String reason, boolean remote) {
				Gdx.app.log("WebSocket", "WSClient closed.");
				connected = false;
			}
		};
		
		wsc.connect();
	}
	
	public void loginTeacherCheck(String message) {
		String userTeacher = message.split(POINTSPLIT)[0];
		String check = message.split(POINTSPLIT)[1];
		
		if(check.equals("NoExist")) {
			TeacherMain.warningDialog.show(userTeacher + " not exists", TeacherMain.teacherLoginRegisterScreen.getStage());
		}
		else if(check.equals("WrongPassword")) {
			TeacherMain.warningDialog.show(userTeacher + " has another password", TeacherMain.teacherLoginRegisterScreen.getStage());
		}
		else {
			teacherLoginDialog.loginSuccess();
		}
	}
	public void registerTeacherCheck(String message) {
		String userTeacher = message.split(POINTSPLIT)[0];
		String check = message.split(POINTSPLIT)[1];
		
		if(check.equals("Failure")) {
			TeacherMain.warningDialog.show(userTeacher + " already exists", TeacherMain.teacherLoginRegisterScreen.getStage());
		}
		else {
			TeacherMain.warningDialog.show(userTeacher + " registered", TeacherMain.teacherLoginRegisterScreen.getStage());
		}
	}
	public void createGameCheck(String message) {
		String gameName = message.split(POINTSPLIT)[0];
		String check = message.split(POINTSPLIT)[1];
		
		if(check.equals("Failure")) {
			TeacherMain.warningDialog.show(gameName + " already exists", TeacherMain.teacherGamesScreen.getStage());
		}
		else {
			TeacherMain.warningDialog.show(gameName + " registered", TeacherMain.teacherGamesScreen.getStage());
		}
	}
	public void askGamesCheck(String message) {
		String [] games = message.split(POINTSPLIT);
		if(!games[0].equals("")) {
			Array<String> newGames = new Array<String>(games);
			this.games.updateGames(newGames);
		}
		else this.games.noGames();
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
	public boolean updateGame(String gameName, String teacher, String password, String tasks) {
		return sendMessage(UPDATEGAME + POINTSPLIT + gameName + POINTSPLIT + teacher + POINTSPLIT + password + POINTSPLIT + tasks);
	}
	public boolean askGamesTeacher(String teacher) {
		return sendMessage(GAMES + POINTSPLIT + teacher);
	}
	public boolean openGame(String gameName, String teacher, String password, String tasks) {
		return sendMessage(OPENGAME + POINTSPLIT + gameName + POINTSPLIT + teacher + POINTSPLIT + password + POINTSPLIT + tasks);
	}
	private boolean sendMessage(String message) {
		if(connected) {
			Gdx.app.log("WebSocket", "WSClient send message: " + message);
			wsc.send(message);
			return true;
		}
		else return false;
	}

	public WebSocketClient getWsc() {
		return wsc;
	}

	public void setTeacherLoginDialog(TeacherLoginDialog teacherLoginDialog) {
		this.teacherLoginDialog = teacherLoginDialog;
	}
	public void setGames(Games games) {
		this.games = games;
	}
}
