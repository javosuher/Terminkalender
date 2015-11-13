package com.project.terminkalender.login;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.Resources;
import com.project.terminkalender.TeacherMain;
import com.project.terminkalender.tools.ScrollWindow;
import com.project.terminkalender.userdata.Game;

public class GamesOpenActor extends Table {
	private GamesOpen games;
	private ScrollWindow gamesWindow;

	public GamesOpenActor(Skin skin) {
		super(skin);
		
		games = new GamesOpen();
		gamesWindow = new ScrollWindow("Games", skin, games.getGamesTable());
		ImageButton backButton = new ImageButton(skin, "imageButtonBack");
		
		setFillParent(true);
		
		add(gamesWindow).width(800).height(TeacherMain.HEIGHT - 16).expand().left().pad(8);
		add(backButton).width(135).height(125).getTable().pad(16);
		
		backButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				AppMain.setNewScreen(AppMain.loginScreen);
			}
		});
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(games.update()) {
			Array<Game> gamesArray = games.getGames();
			Table gamesTable = this.games.getGamesTable();
			
			int actualColumn = 0;
			games.getGamesTable().clear();
			for(final Game game : gamesArray) {
				float width = 200;
				float height = 125;
				
				final TextButton gameButton = new TextButton(game.getName(), Resources.skin);
				float labelHeight = (gameButton.getLabel().getWidth() / gameButton.getWidth() * gameButton.getLabel().getHeight() + height);
				gameButton.getLabel().setWrap(true);
				if(gameButton.getLabel().getWidth() > width) {
					height = labelHeight;
				}
				
				final GameSelectionDialog gameSelectionDialog = new GameSelectionDialog("", game, Resources.skin);
				gamesTable.add(gameButton).width(200).height(125).pad(30);
				
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
