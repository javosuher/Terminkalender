package com.project.terminkalender.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane.SplitPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.TreeStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.project.terminkalender.TeacherMain;

public class kennySkin extends Skin {

	public kennySkin() {
		TextureAtlas blueAtlas = new TextureAtlas("skins/ui-blue.atlas");
		TextureAtlas redAtlas = new TextureAtlas("skins/ui-red.atlas");
		TextureAtlas greenAtlas = new TextureAtlas("skins/ui-green.atlas");
		TextureAtlas orangeAtlas = new TextureAtlas("skins/ui-orange.atlas");
		TextureAtlas whiteAtlas = new TextureAtlas("skins/ui-white.atlas");
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/babyblue.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 20;
		BitmapFont fontMed = generator.generateFont(parameter);
		parameter.size = 40;
		BitmapFont fontBig = generator.generateFont(parameter);
		generator.dispose();
		
		//BitmapFont font12 = new BitmapFont(Gdx.files.internal("skins/kenvector_future-12.fnt"));
		//BitmapFont font18 = new BitmapFont(Gdx.files.internal("skins/kenvector_future-18.fnt"));
		//BitmapFont font32 = new BitmapFont(Gdx.files.internal("skins/kenvector_future-32.fnt"));
		
		addRegions(blueAtlas);
		//add("KenPixelFont12", font12);
		//add("FontMed", font18);
		//add("FontBig", font32);
		add("FontMed", fontMed);
		add("FontBig", fontBig);
		
		final ButtonStyle buttonStyle = new ButtonStyle(getDrawable("button_04"), 
														getDrawable("button_02"), 
														getDrawable("button_04"));
		
		final ButtonStyle calenderButtonStyle = new ButtonStyle(getDrawable("button_01"), 
																getDrawable("button_01"), 
																getDrawable("button_01"));
		
		final TextButtonStyle textButtonStyle = new TextButtonStyle(getDrawable("button_04"), 
																	getDrawable("button_02"), 
																	getDrawable("button_04"), 
																	getFont("FontMed"));
		
		final TextButtonStyle textButtonStyle32 = new TextButtonStyle(getDrawable("button_04"), 
																	  getDrawable("button_02"), 
																	  getDrawable("button_04"), 
																	  getFont("FontBig"));
		
		TextureRegion image = new TextureRegion(TeacherMain.assets.get("folderIcon.png", Texture.class));														 
		final ImageButtonStyle imageButtonStyleFolder = new ImageButtonStyle(getDrawable("button_04"), 
																			 getDrawable("button_02"), 
																			 getDrawable("button_04"), 
																			 new TextureRegionDrawable(image), 
																			 new TextureRegionDrawable(image), 
																			 new TextureRegionDrawable(image));															 
		
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
		
		final WindowStyle windowStyle = new WindowStyle(getFont("FontMed"), 
														Color.BLACK, 
														getDrawable("window_03"));
		
		final WindowStyle window2Style = new WindowStyle(getFont("FontMed"), 
														Color.BLACK, 
														getDrawable("window_02"));
		
		final WindowStyle window3Style = new WindowStyle(getFont("FontMed"), 
														 Color.BLACK, 
														 getDrawable("window_01"));
		
		final WindowStyle windowDialogStyle = new WindowStyle(getFont("FontMed"), 
														Color.BLACK, 
														getDrawable("window_03"));
		
		windowDialogStyle.stageBackground = newDrawable("dialogBackground", new Color(0, 0, 0, 0.45f));
		
		final WindowStyle windowDescriptionStyle = new WindowStyle(getFont("FontMed"), 
				 										 	  	   Color.BLACK, 
				 										 	  	   getDrawable("color_window"));
		
		final ProgressBarStyle progressBarStyle = new ProgressBarStyle(getDrawable("slider_back_hor"), 
																	   getDrawable("knob_01"));
		
		final SliderStyle sliderStyle = new SliderStyle(getDrawable("slider_back_hor"), 
														getDrawable("knob_02"));
		
		final LabelStyle labelStyle = new LabelStyle(getFont("FontMed"), 
													 Color.BLACK);
		
		final TextFieldStyle textFieldStyle = new TextFieldStyle(getFont("FontMed"), 
													 			 Color.BLACK, 
													 			 getDrawable("textbox_cursor_02"), 
													 			 getDrawable("color_basetext"), 
													 			 getDrawable("textbox_02"));
		
		final CheckBoxStyle checkBoxStyle = new CheckBoxStyle(getDrawable("checkbox_off"), 
															  getDrawable("checkbox_on"), 
															  getFont("FontMed"), 
															  Color.BLACK);
		
		final ListStyle listStyle = new ListStyle(getFont("FontMed"), 
												  Color.BLACK, 
												  Color.BLACK, 
												  getDrawable("button_01"));
		
		final SelectBoxStyle selectBoxStyle = new SelectBoxStyle(getFont("FontMed"), 
				 												 Color.BLACK, 
				 												 getDrawable("selectbox_01"), 
				 												 scrollPaneStyle, 
				 												 listStyle);
		
		final TouchpadStyle touchpadStyle = new TouchpadStyle(getDrawable("window_02"), 
															  getDrawable("knob_04"));
		
		final TreeStyle treeStyle = new TreeStyle(getDrawable("icon_arrow_right"), 
												  getDrawable("icon_arrow_down"), 
												  getDrawable("window_03"));
		
		add("default", buttonStyle);
		add("calender", calenderButtonStyle);
		add("default", textButtonStyle);
		add("default32", textButtonStyle32);
		add("imageButtonFolder", imageButtonStyleFolder);
		add("default", scrollPaneStyle);
		add("window", scrollPaneWindowStyle);
		add("default", splitPaneStyle);
		add("default", windowStyle);
		add("window2", window2Style);
		add("window3", window3Style);
		add("windowDialog", windowDialogStyle);
		add("windowDescription", windowDescriptionStyle);
		add("default", progressBarStyle);
		add("default", sliderStyle);
		add("default", labelStyle);
		add("default", textFieldStyle);
        add("default", checkBoxStyle);
        add("default", selectBoxStyle);
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
																	getFont("FontMed"));
        
        add("closeRedButton", closeRedButtonStyle);
        add("redTextButton", redTextButtonStyle);
        
        addRegions(whiteAtlas);
        
        final TextButtonStyle emptyTextButtonDescriptionStyle = new TextButtonStyle(getDrawable("button_01_white"), 
				 														 			getDrawable("button_01_white"), 
				 														 			getDrawable("button_01_white"), 
				 														 			getFont("FontMed"));
        
        add("emptyTextButtonDescription", emptyTextButtonDescriptionStyle);
        
        addRegions(greenAtlas);
        
        final TextButtonStyle greenTextButtonStyle = new TextButtonStyle(getDrawable("button_04_green"), 
																	     getDrawable("button_02_green"), 
																	     getDrawable("button_04_green"), 
																	     getFont("FontMed"));
        
        final TextButtonStyle fullTextButtonDescriptionStyle = new TextButtonStyle(getDrawable("button_01_green"), 
				 																   getDrawable("button_01_green"), 
				 																   getDrawable("button_01_green"), 
				 																   getFont("FontMed"));
        
        final WindowStyle windowDescriptionGreenStyle = new WindowStyle(getFont("FontMed"), 
														Color.BLACK, 
														getDrawable("button_01_green"));
        
        add("greenTextButton", greenTextButtonStyle);
        add("windowDescriptionGreen", windowDescriptionGreenStyle);
        add("fullTextButtonDescription", fullTextButtonDescriptionStyle);
        
        addRegions(orangeAtlas);
        
        final TextButtonStyle orangeTextButtonStyle = new TextButtonStyle(getDrawable("button_04_orange"), 
			     														 getDrawable("button_02_orange"), 
			     														 getDrawable("button_04_orange"), 
			     														 getFont("FontMed"));
        
        add("orangeTextButton", orangeTextButtonStyle);
	}
}
