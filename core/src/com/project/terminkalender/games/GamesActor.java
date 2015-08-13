package com.project.terminkalender.games;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.ScrollWindow;
import com.project.terminkalender.TeacherMain;

public class GamesActor extends Table {
	private Games games;
	private ScrollWindow gamesWindow;

	public GamesActor(Skin skin) {
		super(skin);
		games = new Games();
		
		gamesWindow = new ScrollWindow("Games", skin, games.getGamesTable());
		Table buttonsTable = new Table(skin);
		TextButton newGameButton = new TextButton("New Game", skin);
		TextButton updateGamesButton = new TextButton("Update Games", skin);
		
		final CreateGameDialogActor createGameDialogActor = new CreateGameDialogActor(skin);
		
		setFillParent(true);
		
		add(gamesWindow).width(800).height(TeacherMain.HEIGHT - 16).expand().left().pad(8);
		buttonsTable.add(newGameButton).width(150).height(75).getTable().pad(8).row();
		buttonsTable.add(updateGamesButton).width(150).height(75).pad(8);
		add(buttonsTable);
		
		newGameButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				createGameDialogActor.show(getStage());
			}
		});
		updateGamesButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				games.askGames();
			}
		});
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(games.update()) {
			Array<Game> gamesArray = games.getGames();
			Array<Game> gamesOpenArray = games.getOpenGames();
			
			games.getGamesTable().clear();
			int column = createGamesButtons(gamesArray, TeacherMain.skin.get("default", TextButtonStyle.class), "Game", 0);
			createGamesButtons(gamesOpenArray, TeacherMain.skin.get("redTextButton", TextButtonStyle.class), "OpenGame", column);
			
			games.finishUpdate();
		}
	}
	
	private int createGamesButtons(Array<Game> games, TextButtonStyle textButtonStyle, final String typeGame, int column) {
		Table gamesTable = this.games.getGamesTable();
		int actualColumn = column;
		
		for(final Game game : games) {
			final TextButton gameButton = new TextButton(game.getName(), textButtonStyle);
			gamesTable.add(gameButton).width(200).height(100).pad(30);
			
			++actualColumn;
			if(actualColumn % 3 == 0) {
				gamesTable.row();
			}
			
			gameButton.addListener(new ClickListener() {

				@Override 
				public void clicked(InputEvent event, float x, float y) {
					GameDialog dialogActor = null;
					if(typeGame.equals("Game")) {
						dialogActor = new GameDialogActor(TeacherMain.skin, game, gameButton);
					}
					else if(typeGame.equals("OpenGame")) {
						dialogActor = new OpenGameDialog(TeacherMain.skin, game, gameButton);
					}
					dialogActor.show(getStage());
				}
			});
		}
		
		return actualColumn;
	}
}
