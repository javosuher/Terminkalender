package com.project.terminkalender.calendar;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.Resources;
import com.project.terminkalender.screens.CalendarScreen;
import com.project.terminkalender.tools.DialogActor;
import com.project.terminkalender.userdata.Game;

public class InteractionsDialog extends DialogActor {
	private Table table;
	private Array<String> users;

	public InteractionsDialog(String title, Skin skin) {
		super(title, skin);
		
		center();
		setMovable(false);
		setResizable(false);
		setModal(true);
		pad(20);
		padTop(30);
		
		table = new Table(skin);
		ScrollPane scroll = new ScrollPane(table, skin, "window");
		TextButton acceptButton = new TextButton("OK", skin);
		Game game = AppMain.user.getGame();
		users = new Array<String>(game.getUsers());
		users.removeValue(AppMain.user.getUserName(), false);
		
		scroll.setFadeScrollBars(false);
		
		getButtonTable().defaults().width(175).height(100);
		getContentTable().padTop(20);
		getContentTable().padBottom(5);
		getContentTable().add(scroll).width(600).height(350);
		button(acceptButton);
	}

	@Override
	public Dialog show(Stage stage) {
		construct();
		return super.show(stage);
	}
	private void construct() {
		table.clear();
		
		UsersInteraction usersInteraction = new UsersInteraction(new Array<String>(users));
		CalendarScreen calendarScreen = (CalendarScreen) AppMain.calendarScreen;
		Array<Slot> slots = calendarScreen.getCalendarSlots();
		
		for(Slot slot : slots) {
			TaskCalendar task = slot.getTask();
			if(!task.getDescription().equals("")) {
				Array<String> partners = task.getPartners();
				for(String partner : partners) {
					usersInteraction.addInteraction(partner);
				}
			}
		}
		usersInteraction.sort();
		
		table.add(new Label("Benutzer     ", Resources.skin)).left();
		table.add(new Label("Interaktion", Resources.skin)).right().row();
		for(int index = 0; index < usersInteraction.getSize(); ++index) {
			table.add(usersInteraction.getIteractionNameByIndex(index)).left();
			table.add(Integer.toString(usersInteraction.getIteractionNumByIndex(index))).right().row();
		}
	}
	private class UsersInteraction {
		private Array<String> names;
		private Array<Integer> numberInteractions;
		
		public UsersInteraction(Array<String> names) {
			this.names = names;
			numberInteractions = new Array<Integer>();
			for(int index = 0; index < names.size; ++index) {
				numberInteractions.add(0);
			}
		}
		
		public void addInteraction(String user) {
			for(int index = 0; index < names.size; ++index) {
				if(names.get(index).equals(user)) {
					numberInteractions.set(index, numberInteractions.get(index) + 1);
					index = names.size;
				}
			}
		}
		
		public void sort() {
			for(int index = 1; index < names.size; ++index) {
				for(int secondIndex = index - 1; secondIndex >= 0; --secondIndex) {
					if(numberInteractions.get(index) <= numberInteractions.get(secondIndex)) {
						changePosition(index, secondIndex + 1);
						secondIndex = -1;
					}
					else if(secondIndex == 0 && numberInteractions.get(index) > numberInteractions.get(secondIndex)) {
						changePosition(index, 0);
					}
				}
			}
		}
		private void changePosition(int beforeIndex, int newIndex) {
			int value = numberInteractions.removeIndex(beforeIndex);
			String name = names.removeIndex(beforeIndex);
			numberInteractions.insert(newIndex, value);
			names.insert(newIndex, name);
		}

		public int getSize() {
			return names.size;
		}
		public String getIteractionNameByIndex(int index) {
			return names.get(index);
		}
		public int getIteractionNumByIndex(int index) {
			return numberInteractions.get(index);
		}
	}
}
