package com.project.terminkalender.games;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;
import com.project.terminkalender.TeacherMain;

public class GamesActor extends Table {
	private Games games;
	private Window gamesWindow;

	public GamesActor(Skin skin) {
		super(skin);
		games = new Games();
		
		gamesWindow = new Window("Games", skin);
		TextButton newGameButton = new TextButton("New Game", skin);
		Table gamesTable = games.getGamesTable();
		ScrollPane scrollGamesWindow = new ScrollPane(gamesTable, skin);
		final CreateGameDialogActor createGameDialogActor = new CreateGameDialogActor(skin);
		
		gamesWindow.setMovable(false);
		setFillParent(true);
		
		gamesWindow.add(scrollGamesWindow).width(800).height(Main.HEIGHT - 16);
		add(gamesWindow).width(800).height(Main.HEIGHT - 16).expand().left().pad(8);
		add(newGameButton).width(150).height(75).expand().right().pad(8);
		
		newGameButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				createGameDialogActor.show(getStage());
			}
		});
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(games.update()) {
			Table gamesTable = games.getGamesTable();
			Array<Game> gamesArray = games.getGames();
			Skin skin = TeacherMain.assets.get("skins/uiskin.json", Skin.class);
			
			gamesTable.clear();
			
			int column = 0;
			for(Game game : gamesArray) {
				final TextButton gameButton = new TextButton(game.getName(), skin);
				gamesTable.add(gameButton).width(200).height(100).pad(30);
				final GameDialogActor gameDialogActor = new GameDialogActor(skin, game, gameButton);
				
				++column;
				if(column % 3 == 0) {
					gamesTable.row();
				}
				
				gameButton.addListener(new ClickListener() {

					@Override 
					public void clicked(InputEvent event, float x, float y) {
						gameDialogActor.show(getStage());
					}
				});
			}
			games.finishUpdate();
		}
	}
}
