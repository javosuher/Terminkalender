package com.project.terminkalender.login;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;
import com.project.terminkalender.TeacherMain;
import com.project.terminkalender.tools.ScrollWindow;

public class GamesOpenActor extends Table {
	private GamesOpen games;
	private ScrollWindow gamesWindow;

	public GamesOpenActor(Skin skin) {
		super(skin);
		
		games = new GamesOpen();
		gamesWindow = new ScrollWindow("Games", skin, games.getGamesTable());
		TextButton backButton = new TextButton("Back", Main.skin);
		
		setFillParent(true);
		
		add(gamesWindow).width(800).height(TeacherMain.HEIGHT - 16).expand().left().pad(8);
		add(backButton).width(150).height(75).getTable().pad(8);
		
		backButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				Main.setNewScreen(Main.loginScreen);
			}
		});
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(games.update()) {
			Array<GameOpen> gamesArray = games.getGames();
			Table gamesTable = this.games.getGamesTable();
			
			int actualColumn = 0;
			games.getGamesTable().clear();
			for(final GameOpen game : gamesArray) {
				final TextButton gameButton = new TextButton(game.getName(), Main.skin);
				final GameSelectionDialog gameSelectionDialog = new GameSelectionDialog("Hola", game, Main.skin);
				gamesTable.add(gameButton).width(200).height(100).pad(30);
				
				++actualColumn;
				if(actualColumn % 3 == 0) {
					gamesTable.row();
				}
				
				gameButton.addListener(new ClickListener() {

					@Override 
					public void clicked(InputEvent event, float x, float y) {
						gameSelectionDialog.show(getStage());
					}
				});
			}
			games.finishUpdate();
		}
	}
	
	public void updateGames(Array<String> games) {
		this.games.updateGames(games);
	}
	
	public GamesOpen getGames() {
		return games;
	}
}
