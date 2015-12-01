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
import com.project.terminkalender.Resources;

public class KennySkin extends Skin {
	public static final String TEACHER = "Teacher";
	public static final String APP = "App";

	public KennySkin(String application) {
		TextureAtlas blueAtlas = new TextureAtlas("skins/ui-blue.atlas");
		TextureAtlas redAtlas = new TextureAtlas("skins/ui-red.atlas");
		TextureAtlas greenAtlas = new TextureAtlas("skins/ui-green.atlas");
		TextureAtlas orangeAtlas = new TextureAtlas("skins/ui-orange.atlas");
		TextureAtlas whiteAtlas = new TextureAtlas("skins/ui-white.atlas");
		TextureAtlas yellowAtlas = new TextureAtlas("skins/ui-yellow.atlas");
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Blogger_Sans.otf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		int medSize = 10, bigSize = 20, largeSize = 15;
		if(application.equals(APP)) {
			medSize = 35;
			bigSize = 60;
			largeSize = 50;
		}
		else if(application.equals(TEACHER)) {
			medSize = 20;
			bigSize = 70;
			largeSize = 30;
		}
		parameter.size = medSize;
		BitmapFont fontMed = generator.generateFont(parameter);
		parameter.size = bigSize;
		BitmapFont fontBig = generator.generateFont(parameter);
		parameter.size = largeSize;
		BitmapFont fontLarge = generator.generateFont(parameter);
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
		add("FontLarge", fontLarge);
		
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
		
		final TextButtonStyle textButtonStyleLarge = new TextButtonStyle(getDrawable("button_04"), 
																 	getDrawable("button_02"), 
																 	getDrawable("button_04"), 
																 	getFont("FontLarge"));
		
		final TextButtonStyle textButtonStyleBig = new TextButtonStyle(getDrawable("button_04"), 
																	  getDrawable("button_02"), 
																	  getDrawable("button_04"), 
																	  getFont("FontBig"));
		
		TextureRegion image = new TextureRegion(Resources.assets.get("folderIcon.png", Texture.class));														 
		final ImageButtonStyle imageButtonStyleFolder = new ImageButtonStyle(getDrawable("button_04"), 
																			 getDrawable("button_02"), 
																			 getDrawable("button_04"), 
																			 new TextureRegionDrawable(image), 
																			 new TextureRegionDrawable(image), 
																			 new TextureRegionDrawable(image));
		
		image = new TextureRegion(Resources.assets.get("arrowLeft.png", Texture.class));														 
		final ImageButtonStyle imageButtonStyleArrowLeft = new ImageButtonStyle(getDrawable("button_04"), 
																			 getDrawable("button_02"), 
																			 getDrawable("button_04"), 
																			 new TextureRegionDrawable(image), 
																			 new TextureRegionDrawable(image), 
																			 new TextureRegionDrawable(image));
		
		image = new TextureRegion(Resources.assets.get("connect.png", Texture.class));														 
		final ImageButtonStyle imageButtonStyleReconnect = new ImageButtonStyle(getDrawable("button_04"), 
																			 getDrawable("button_02"), 
																			 getDrawable("button_04"), 
																			 new TextureRegionDrawable(image), 
																			 new TextureRegionDrawable(image), 
																			 new TextureRegionDrawable(image));	
		
		image = new TextureRegion(Resources.assets.get("return.png", Texture.class));														 
		final ImageButtonStyle imageButtonStyleBack = new ImageButtonStyle(getDrawable("button_04"), 
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
		
		final ScrollPaneStyle scrollPaneSelectBoxStyle = new ScrollPaneStyle(getDrawable("textbox_02"), 
																			 getDrawable("scroll_back_hor"), 
																			 getDrawable("knob_06"), 
																			 getDrawable("scroll_back_ver"), 
																			 getDrawable("knob_05"));
		
		final ScrollPaneStyle scrollPaneWindowStyle = new ScrollPaneStyle(getDrawable("color_window"), 
																	getDrawable("scroll_back_hor"), 
																	getDrawable("knob_06"), 
																	getDrawable("scroll_back_ver"), 
																	getDrawable("knob_05"));
		
		final ScrollPaneStyle scrollPaneWindowTasksCalendarStyle = new ScrollPaneStyle(getDrawable("color_window"), 
																		  getDrawable("scroll_back_hor"), 
																		  getDrawable("knob_06"), 
																		  getDrawable("Hor_Scroll_tasks"), 
																		  getDrawable("knob_Scroll_tasks"));
		
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
		
		final WindowStyle windowDialogTextFieldStyle = new WindowStyle(getFont("FontMed"), 
															  		   Color.BLACK, 
															  		   getDrawable("transparency"));
		
		windowDialogTextFieldStyle.stageBackground = newDrawable("dialogBackground", new Color(0, 0, 0, 0.45f));
		
		final WindowStyle windowDescriptionStyle = new WindowStyle(getFont("FontMed"), 
				 										 	  	   Color.BLACK, 
				 										 	  	   getDrawable("color_window"));
		
		final ProgressBarStyle progressBarStyle = new ProgressBarStyle(getDrawable("slider_back_hor"), 
																	   getDrawable("knob_01"));
		
		final SliderStyle sliderStyle = new SliderStyle(getDrawable("slider_back_hor"), 
														getDrawable("knob_02"));
		
		final LabelStyle labelStyle = new LabelStyle(getFont("FontMed"), 
													 Color.BLACK);
		
		final LabelStyle labelBigStyle = new LabelStyle(getFont("FontLarge"), 
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
				 												 scrollPaneSelectBoxStyle, 
				 												 listStyle);
		
		final TouchpadStyle touchpadStyle = new TouchpadStyle(getDrawable("window_02"), 
															  getDrawable("knob_04"));
		
		final TreeStyle treeStyle = new TreeStyle(getDrawable("icon_arrow_right"), 
												  getDrawable("icon_arrow_down"), 
												  getDrawable("window_03"));
		
		add("default", buttonStyle);
		add("calender", calenderButtonStyle);
		add("default", textButtonStyle);
		add("defaultBig", textButtonStyleBig); 
		add("textButtonLarge", textButtonStyleLarge);
		add("imageButtonFolder", imageButtonStyleFolder); 
		add("imageButtonArrowLeft", imageButtonStyleArrowLeft); 
		add("imageButtonReconnect", imageButtonStyleReconnect);
		add("imageButtonBack", imageButtonStyleBack);
		add("default", scrollPaneStyle);
		add("window", scrollPaneWindowStyle);
		add("scrollPaneWindowTasksCalendar", scrollPaneWindowTasksCalendarStyle);
		add("default", splitPaneStyle);
		add("default", windowStyle);
		add("window2", window2Style);
		add("window3", window3Style);
		add("windowDialog", windowDialogStyle);
		add("windowDialogTextField", windowDialogTextFieldStyle);
		add("windowDescription", windowDescriptionStyle);
		add("default", progressBarStyle);
		add("default", sliderStyle);
		add("default", labelStyle);
		add("labelBig", labelBigStyle);
		add("default", textFieldStyle);
        add("default", checkBoxStyle);
        add("default", selectBoxStyle);
        add("default", listStyle);
        add("default", touchpadStyle);
        add("default", treeStyle);
        
        addRegions(redAtlas);
        
        TextureRegion cross = new TextureRegion(Resources.assets.get("cross.png", Texture.class));
        TextureRegion redCircle = new TextureRegion(Resources.assets.get("redCircle.png", Texture.class));
        
        final ButtonStyle closeRedButtonStyle = new ButtonStyle(new TextureRegionDrawable(cross), 
        														new TextureRegionDrawable(redCircle), 
        														new TextureRegionDrawable(cross));
        
        final TextButtonStyle textButtonStyleLargeRed = new TextButtonStyle(getDrawable("button_04_red"), 
			 	  															  getDrawable("button_02_red"), 
			 	  															  getDrawable("button_04_red"), 
			 	  															  getFont("FontLarge"));
        
        final TextButtonStyle redTextButtonStyle = new TextButtonStyle(getDrawable("button_04_red"), 
																	getDrawable("button_02_red"), 
																	getDrawable("button_04_red"), 
																	getFont("FontMed"));
        
        image = new TextureRegion(Resources.assets.get("return.png", Texture.class));													 
		final ImageButtonStyle imageButtonStylehideKeyboard = new ImageButtonStyle(getDrawable("button_04_red"), 
																			 	getDrawable("button_02_red"), 
																			 	getDrawable("button_04_red"), 
																			 	new TextureRegionDrawable(image), 
																			 	new TextureRegionDrawable(image), 
																			 	new TextureRegionDrawable(image));
		
		final TextButtonStyle textButtonTaskRedStyle = new TextButtonStyle(getDrawable("button_01_red"), 
				   														   getDrawable("button_01_red"), 
				   														   getDrawable("button_01_red"), 
				   														   getFont("FontMed"));
        
        add("closeRedButton", closeRedButtonStyle);
        add("textButtonLargeRed", textButtonStyleLargeRed);
        add("redTextButton", redTextButtonStyle);
        add("imageButtonhideKeyboard", imageButtonStylehideKeyboard);
        add("textButtonTaskRed", textButtonTaskRedStyle);
        
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
        
        final TextButtonStyle textButtonStyleLargeGreen = new TextButtonStyle(getDrawable("button_04_green"), 
			 															 	  getDrawable("button_02_green"), 
			 															 	  getDrawable("button_04_green"), 
			 															 	  getFont("FontLarge"));
        
        final TextButtonStyle fullTextButtonDescriptionStyle = new TextButtonStyle(getDrawable("button_01_green"), 
				 																   getDrawable("button_01_green"), 
				 																   getDrawable("button_01_green"), 
				 																   getFont("FontMed"));
        
        
        final WindowStyle windowDescriptionGreenStyle = new WindowStyle(getFont("FontMed"), 
																		Color.BLACK, 
																		getDrawable("button_01_green"));
        
        add("greenTextButton", greenTextButtonStyle);
        add("textButtonLargeGreen", textButtonStyleLargeGreen);
        add("windowDescriptionGreen", windowDescriptionGreenStyle);
        add("fullTextButtonDescription", fullTextButtonDescriptionStyle);
        
        addRegions(orangeAtlas);
        
        final TextButtonStyle orangeTextButtonStyle = new TextButtonStyle(getDrawable("button_04_orange"), 
			     														 getDrawable("button_02_orange"), 
			     														 getDrawable("button_04_orange"), 
			     														 getFont("FontMed"));
        
        image = new TextureRegion(Resources.assets.get("reconnect.png", Texture.class));														 
		final ImageButtonStyle orangeImageButtonStyleReconnect = new ImageButtonStyle(getDrawable("button_04_orange"), 
																			 getDrawable("button_02_orange"), 
																			 getDrawable("button_04_orange"), 
																			 new TextureRegionDrawable(image), 
																			 new TextureRegionDrawable(image), 
																			 new TextureRegionDrawable(image));
		
		final TextButtonStyle chatUserStyleOrange = new TextButtonStyle(getDrawable("button_01_orange"), 
					  													getDrawable("button_01_orange"), 
					  													getDrawable("button_01_orange"), 
					  													getFont("FontMed"));
        
        add("orangeTextButton", orangeTextButtonStyle);
        add("orangeImageButtonReconnect", orangeImageButtonStyleReconnect);
        add("chatUserOrange", chatUserStyleOrange);
        
        addRegions(yellowAtlas);
        
        final TextButtonStyle chatUserStyleYellow = new TextButtonStyle(getDrawable("button_01_yellow"), 
				   												  		getDrawable("button_01_yellow"), 
				   												  		getDrawable("button_01_yellow"), 
				   												  		getFont("FontMed"));
        
        add("chatUserYellow", chatUserStyleYellow);
	}
}
