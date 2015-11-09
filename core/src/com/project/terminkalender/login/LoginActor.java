package com.project.terminkalender.login;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.Resources;
import com.project.terminkalender.tools.TWindow;

public class LoginActor extends Table {

	public LoginActor(Skin skin) {
		super(skin);
		
		Window loginWindow = new TWindow("", skin, "window2");
		Label teacherName = new Label("Teacher", skin);
		final TextField teacherNameText = new TextField("", skin);
		TextButton loginButton = new TextButton("Enter", skin);
		
		Gdx.input.isPeripheralAvailable(Input.Peripheral.OnscreenKeyboard);
		//Gdx.input.setOnscreenKeyboardVisible(boolean visible);
		
		teacherNameText.setOnscreenKeyboard(new TextField.OnscreenKeyboard() {
			@Override
			public void show(boolean visible) {
				String h = "";
				if(visible) h = "true";
				else h = "false";
				Gdx.app.log("Paso", h);
				Gdx.input.setOnscreenKeyboardVisible(visible);
				TextField x = new TextField("", Resources.skin);
				getStage().addActor(x);
				x.setVisible(visible);
			}
	    });
		
		/*teacherNameText.setOnscreenKeyboard(new TextField.OnscreenKeyboard() {
	        @Override
	        public void show(boolean visible) {
	            //Gdx.input.setOnscreenKeyboardVisible(true);
	            Gdx.input.getTextInput(new Input.TextInputListener() {
	                @Override
	                public void input(String text) {
	                	teacherNameText.setText(text);
	                }

	                @Override
	                public void canceled() {
	                    System.out.println("Cancelled.");
	                }
	            }, "Title", "Default text...", "hola");
	        }
	    });*/
		
		/*final Table table = this; 
		final TextField textField = new TextField("", Resources.skin);
		textField.setY(310);
		textField.setX(0);
		textField.setVisible(false);
		if(Gdx.app.getType() == ApplicationType.Android) {
			teacherNameText.setOnscreenKeyboard(new OnscreenKeyboard() {
				@Override
				public void show(boolean visible) {
					Gdx.input.setOnscreenKeyboardVisible(visible);
					if (visible) {
						textField.setVisible(true);
					} else {
						textField.setVisible(false);
					}
				}
			});
		}
		
		this.add(textField).width(310);*/

		setFillParent(true);
		
		loginWindow.add(teacherName).row();
		loginWindow.add(teacherNameText).row();
		loginWindow.add(loginButton).width(100).height(50).colspan(2).padTop(40);
		add(loginWindow).center();
		
		loginButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				AppMain.user.login(teacherNameText.getText());
			}
		});
	}
}