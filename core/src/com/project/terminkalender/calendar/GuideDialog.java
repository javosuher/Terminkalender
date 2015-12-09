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
		Label guide1Label = new Label("1. Schau, was du machen kannst!", skin);
		Image image1 = new Image(Resources.assets.get("guide/1.png", Texture.class));
		Label guide2Label = new Label("2. Schau mit wie vielen Spielern du\n   eine Aktivität machen kannst!", skin);
		Image image2 = new Image(Resources.assets.get("guide/2.png", Texture.class));
		Label guide3Label = new Label("3. Benutz den Chat und sprich\n   mit deinen Spielpartnern!", skin);
		Image image31 = new Image(Resources.assets.get("guide/3.png", Texture.class));
		Image image32 = new Image(Resources.assets.get("guide/4.png", Texture.class));
		Label guide4Label = new Label("4. Sprich mit deinen Spielpartnern und\n   ergänz deinen Terminkalender!", skin);
		Image image4 = new Image(Resources.assets.get("guide/5.png", Texture.class));
		Label guide5Label = new Label("5. Ergänz deinen Terminkalender mit\n   den Daten deiner Spielpartner!", skin);
		Image image5 = new Image(Resources.assets.get("guide/6.png", Texture.class));
		Label guide6Label = new Label("6. Validier deine Aktivitäten!", skin);
		Image image6 = new Image(Resources.assets.get("guide/7.png", Texture.class));
		Label guide7Label = new Label("7. Achtung!! Die Farbe grün bedeutet,\n   dass deine Informationen und die\n   Informationen von deinem Spielpartner\n   identisch sind. Rot bedeutet, dass Eure\n   Informationen nicht identisch sind.", skin);
		Image image7 = new Image(Resources.assets.get("guide/8.png", Texture.class));
		Label guide8Label = new Label("8. Ergänz deinen Terminkalender!", skin);
		Image image8 = new Image(Resources.assets.get("guide/9.png", Texture.class));
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
		table.add(image6).width(image1.getWidth() / 2).height(image1.getHeight() / 2).padBottom(20).row();
		table.add(guide7Label).padBottom(20).row();
		table.add(image7).width(image1.getWidth() / 2).height(image1.getHeight() / 2).padBottom(20).row();
		table.add(guide8Label).padBottom(20).row();
		table.add(image8).width(image1.getWidth() / 2).height(image1.getHeight() / 2).padBottom(20).row();
		
		scroll.setFadeScrollBars(false);
		
		getButtonTable().defaults().width(175).height(100);
		getContentTable().padTop(20);
		getContentTable().padBottom(5);
		getContentTable().add(scroll).width(600).height(350);
		button(acceptButton);
	}
	
}
