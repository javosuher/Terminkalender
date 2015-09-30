package com.project.terminkalender.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane.SplitPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.TreeStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;

public class kennySkin extends Skin {

	public kennySkin() {
		TextureAtlas blueAtlas = new TextureAtlas("skins/ui-blue.atlas");
		TextureAtlas redAtlas = new TextureAtlas("skins/ui-red.atlas");
		TextureAtlas greenAtlas = new TextureAtlas("skins/ui-green.atlas");
		TextureAtlas orangeAtlas = new TextureAtlas("skins/ui-orange.atlas");
		TextureAtlas whiteAtlas = new TextureAtlas("skins/ui-white.atlas");
		
		BitmapFont font12 = new BitmapFont(Gdx.files.internal("skins/kenpixel_mini_square-12.fnt"));
		BitmapFont font18 = new BitmapFont(Gdx.files.internal("skins/kenpixel_mini_square-18.fnt"));
		BitmapFont font32 = new BitmapFont(Gdx.files.internal("skins/kenpixel_mini_square-32.fnt"));
		
		addRegions(blueAtlas);
		add("KenPixelFont12", font12);
		add("KenPixelFont18", font18);
		add("KenPixelFont32", font32);
		
		final ButtonStyle buttonStyle = new ButtonStyle(getDrawable("button_04"), 
														getDrawable("button_02"), 
														getDrawable("button_04"));
		
		final ButtonStyle calenderButtonStyle = new ButtonStyle(getDrawable("button_01"), 
																getDrawable("button_01"), 
																getDrawable("button_01"));
		
		final TextButtonStyle textButtonStyle = new TextButtonStyle(getDrawable("button_04"), 
																	getDrawable("button_02"), 
																	getDrawable("button_04"), 
																	getFont("KenPixelFont18"));
		
		final TextButtonStyle textButtonStyle32 = new TextButtonStyle(getDrawable("button_04"), 
																	getDrawable("button_02"), 
																	getDrawable("button_04"), 
																	getFont("KenPixelFont32"));
		
		final ScrollPaneStyle scrollPaneStyle = new ScrollPaneStyle(getDrawable("color_widgettext"), 
																	getDrawable("scroll_back_hor"), 
																	getDrawable("knob_06"), 
																	getDrawable("scroll_back_ver"), 
																	getDrawable("knob_05"));
		final ScrollPaneStyle scrollPaneWindowStyle = new ScrollPaneStyle(getDrawable("color_window"), 
																	getDrawable("scroll_back_hor"), 
																	getDrawable("knob_06"), 
																	getDrawable("scroll_back_ver"), 
																	getDrawable("knob_05"));
		
		final SplitPaneStyle splitPaneStyle = new SplitPaneStyle(getDrawable("slider_back_hor"));
		
		final WindowStyle windowStyle = new WindowStyle(getFont("KenPixelFont18"), 
														Color.BLACK, 
														getDrawable("window_03"));
		
		final WindowStyle window2Style = new WindowStyle(getFont("KenPixelFont18"), 
														Color.BLACK, 
														getDrawable("window_02"));
		
		final WindowStyle window3Style = new WindowStyle(getFont("KenPixelFont18"), 
														 Color.BLACK, 
														 getDrawable("window_01"));
		
		final WindowStyle windowDescriptionStyle = new WindowStyle(getFont("KenPixelFont18"), 
				 										 	  	   Color.BLACK, 
				 										 	  	   getDrawable("color_window"));
		
		final ProgressBarStyle progressBarStyle = new ProgressBarStyle(getDrawable("slider_back_hor"), 
																	   getDrawable("knob_01"));
		
		final SliderStyle sliderStyle = new SliderStyle(getDrawable("slider_back_hor"), 
														getDrawable("knob_02"));
		
		final LabelStyle labelStyle = new LabelStyle(getFont("KenPixelFont18"), 
													 Color.BLACK);
		
		final TextFieldStyle textFieldStyle = new TextFieldStyle(getFont("KenPixelFont18"), 
													 			 Color.BLACK, 
													 			 getDrawable("textbox_cursor_02"), 
													 			 getDrawable("color_basetext"), 
													 			 getDrawable("textbox_02"));
		
		final CheckBoxStyle checkBoxStyle = new CheckBoxStyle(getDrawable("checkbox_off"), 
															  getDrawable("checkbox_on"), 
															  getFont("KenPixelFont18"), 
															  Color.BLACK);
		
		final ListStyle listStyle = new ListStyle(getFont("KenPixelFont18"), 
												  Color.BLACK, 
												  Color.BLACK, 
												  getDrawable("button_01"));
		
		final TouchpadStyle touchpadStyle = new TouchpadStyle(getDrawable("window_02"), 
															  getDrawable("knob_04"));
		
		final TreeStyle treeStyle = new TreeStyle(getDrawable("icon_arrow_right"), 
												  getDrawable("icon_arrow_down"), 
												  getDrawable("window_03"));
		
		add("default", buttonStyle);
		add("calender", calenderButtonStyle);
		add("default", textButtonStyle);
		add("default32", textButtonStyle32);
		add("default", scrollPaneStyle);
		add("window", scrollPaneWindowStyle);
		add("default", splitPaneStyle);
		add("default", windowStyle);
		add("window2", window2Style);
		add("window3", window3Style);
		add("windowDescription", windowDescriptionStyle);
		add("default", progressBarStyle);
		add("default", sliderStyle);
		add("default", labelStyle);
		add("default", textFieldStyle);
        add("default", checkBoxStyle);
        add("default", listStyle);
        add("default", touchpadStyle);
        add("default", treeStyle);
        
        addRegions(redAtlas);
        
        final ButtonStyle closeRedButtonStyle = new ButtonStyle(getDrawable("button_cross_red"), 
				 											 getDrawable("icon_circle_red"), 
				 											 getDrawable("button_cross_red"));
        
        final TextButtonStyle redTextButtonStyle = new TextButtonStyle(getDrawable("button_04_red"), 
																	getDrawable("button_02_red"), 
																	getDrawable("button_04_red"), 
																	getFont("KenPixelFont18"));
        
        add("closeRedButton", closeRedButtonStyle);
        add("redTextButton", redTextButtonStyle);
        
        addRegions(whiteAtlas);
        
        final TextButtonStyle emptyTextButtonDescriptionStyle = new TextButtonStyle(getDrawable("button_01_white"), 
				 														 			getDrawable("button_01_white"), 
				 														 			getDrawable("button_01_white"), 
				 														 			getFont("KenPixelFont18"));
        
        add("emptyTextButtonDescription", emptyTextButtonDescriptionStyle);
        
        addRegions(greenAtlas);
        
        final TextButtonStyle greenTextButtonStyle = new TextButtonStyle(getDrawable("button_04_green"), 
																	     getDrawable("button_02_green"), 
																	     getDrawable("button_04_green"), 
																	     getFont("KenPixelFont18"));
        
        final TextButtonStyle fullTextButtonDescriptionStyle = new TextButtonStyle(getDrawable("button_01_green"), 
				 																   getDrawable("button_01_green"), 
				 																   getDrawable("button_01_green"), 
				 																   getFont("KenPixelFont18"));
        
        final WindowStyle windowDescriptionGreenStyle = new WindowStyle(getFont("KenPixelFont18"), 
														Color.BLACK, 
														getDrawable("button_01_green"));
        
        add("greenTextButton", greenTextButtonStyle);
        add("windowDescriptionGreen", windowDescriptionGreenStyle);
        add("fullTextButtonDescription", fullTextButtonDescriptionStyle);
        
        addRegions(orangeAtlas);
        
        final TextButtonStyle orangeTextButtonStyle = new TextButtonStyle(getDrawable("button_04_orange"), 
			     														 getDrawable("button_02_orange"), 
			     														 getDrawable("button_04_orange"), 
			     														 getFont("KenPixelFont18"));
        
        add("orangeTextButton", orangeTextButtonStyle);
	}	
}
