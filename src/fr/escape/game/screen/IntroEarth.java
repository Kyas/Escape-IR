/*****************************************************************************
 * 
  * Copyright 2012 See AUTHORS file.
 * 
 * This file is part of Escape-IR.
 * 
 * Escape-IR is free software: you can redistribute it and/or modify
 * it under the terms of the zlib license. See the COPYING file.
 * 
 *****************************************************************************/

package fr.escape.game.screen;

import java.awt.Color;
import java.awt.Font;

import fr.escape.app.Input;
import fr.escape.app.Screen;
import fr.escape.game.Escape;
import fr.escape.resources.font.FontLoader;

public final class IntroEarth implements Screen {
	
	private final static String TITLE = "Earth";
	private final static long WAIT = 3000;
	
	private final Escape game;
	private final Font font;
	private final Runnable next;
	
	private long time;
	
	public IntroEarth(Escape game) {
		
		this.game = game;
		this.font = game.getResources().getFont(FontLoader.VISITOR_ID);
		this.next = new Runnable() {
			
			@Override
			public void run() {
				next();
			}
			
		};
	}
	
	@Override
	public void render(long delta) {
		
		time += delta;
		
		Screens.drawStringInCenterPosition(
				game.getGraphics(), TITLE, 
				game.getGraphics().getWidth() / 2, 
				game.getGraphics().getHeight() / 2, 
				font, Color.BLACK
		);
		
		if(time > WAIT) {
			game.getActivity().post(next);
		}
		
	}

	@Override
	public void show() {
		game.getOverlay().hide();
		time = 0;
	}

	@Override
	public void hide() {
		time = 0;
	}

	@Override
	public boolean touch(Input i) {
		return true;
	}

	@Override
	public boolean move(Input i) {
		return false;
	}

	public void next() {
		game.setEarthScreen();
	}
	
}
