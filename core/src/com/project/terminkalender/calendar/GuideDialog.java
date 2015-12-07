package com.project.terminkalender.calendar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.project.terminkalender.Resources;
import com.project.terminkalender.tools.DialogActor;

public class GuideDialog extends DialogActor {

	public GuideDialog(String title, Skin skin) {
		super(title, skin);
		
		center();
		setMovable(false);
		setResizable(false);
		setModal(true);
		pad(20);
		padTop(30);
		
		Table table = new Table(skin);
		ScrollPane scroll = new ScrollPane(table, skin, "window");
		Label guide1Label = new Label("1. Mira las actividades disponibles", skin);
		Image image1 = new Image(Resources.assets.get("guide/1.png", Texture.class));
		Label guide2Label = new Label("2. Mira con cuantos compañeros \n   necesitas hacer una actividad", skin);
		Image image2 = new Image(Resources.assets.get("guide/2.png", Texture.class));
		Label guide3Label = new Label("3. Habla en el chat con tus \n   compañeros para completar las \n   actividades en tu calendario", skin);
		Image image31 = new Image(Resources.assets.get("guide/3.png", Texture.class));
		Image image32 = new Image(Resources.assets.get("guide/4.png", Texture.class));
		Label guide4Label = new Label("4. Rellena tu calendario con \n   las actividades poniendote \n   de acuerdo con tus compañeros", skin);
		Image image4 = new Image(Resources.assets.get("guide/5.png", Texture.class));
		Label guide5Label = new Label("5. Completa todos los datos \n   acorde a tus compañeros", skin);
		Image image5 = new Image(Resources.assets.get("guide/6.png", Texture.class));
		Label guide6Label = new Label("6. Valida tus actividades. \n   Si se ilumina la actividad en rojo, \n   los datos de tus compañeros \n  y los tuyos no concuerdan, \n   habla de nuevo con ellos \n   para corregirlos", skin);
		Image image61 = new Image(Resources.assets.get("guide/7.png", Texture.class));
		Image image62 = new Image(Resources.assets.get("guide/8.png", Texture.class));
		Label guide7Label = new Label("7. Completa tu calendario \n   correctamente para finalizar \n   el juego", skin);
		Image image7 = new Image(Resources.assets.get("guide/9.png", Texture.class));
		TextButton acceptButton = new TextButton("OK", skin);
		
		table.add(guide1Label).padBottom(20).row();
		table.add(image1).width(image1.getWidth() / 2).height(image1.getHeight() / 2).padBottom(20).row();
		table.add(guide2Label).padBottom(20).row();
		table.add(image2).width(image1.getWidth() / 2).height(image1.getHeight() / 2).padBottom(20).row();
		table.add(guide3Label).padBottom(20).row();
		table.add(image31).width(image1.getWidth() / 2).height(image1.getHeight() / 2).padBottom(20).row();
		table.add(image32).width(image1.getWidth() / 2).height(image1.getHeight() / 2).padBottom(20).row();
		table.add(guide4Label).padBottom(20).row();
		table.add(image4).width(image1.getWidth() / 2).height(image1.getHeight() / 2).padBottom(20).row();
		table.add(guide5Label).padBottom(20).row();
		table.add(image5).width(image1.getWidth() / 2).height(image1.getHeight() / 2).padBottom(20).row();
		table.add(guide6Label).padBottom(20).row();
		table.add(image61).width(image1.getWidth() / 2).height(image1.getHeight() / 2).padBottom(20).row();
		table.add(image62).width(image1.getWidth() / 2).height(image1.getHeight() / 2).padBottom(20).row();
		table.add(guide7Label).padBottom(20).row();
		table.add(image7).width(image1.getWidth() / 2).height(image1.getHeight() / 2).padBottom(20).row();
		
		scroll.setFadeScrollBars(false);
		
		getButtonTable().defaults().width(175).height(100);
		getContentTable().padTop(20);
		getContentTable().padBottom(5);
		getContentTable().add(scroll).width(600).height(350);
		button(acceptButton);
	}
	
}
