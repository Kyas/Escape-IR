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

import java.io.File;
import java.io.IOException;

import fr.escape.app.Screen;
import fr.escape.game.Escape;
import fr.escape.graphics.RepeatableScrollingTexture;
import fr.escape.graphics.ScrollingTexture;
import fr.escape.graphics.Texture;

public class Splash implements Screen {

	private final static String TAG = Splash.class.getSimpleName();
	
	private final Escape game;
	private Texture logo;
	private ScrollingTexture background;
	private long time;
	
	public Splash(Escape game) {
		
		this.game = game;
		this.time = 0;
		
		try {
			
			// this.logo = new Texture(new File("res/ScrollingBackground.jpg"));
			this.logo = new Texture(new File("res/Escape-IR.png"));
			this.background = new RepeatableScrollingTexture(new Texture(new File("res/04.jpg")));
			
		} catch(IOException e) {
			game.getActivity().error(TAG, "Cannot load a required Texture", e);
			game.getActivity().exit();
		}
	}
	
	@Override
	public void render(long delta) {

		time += delta;
		
		game.getActivity().debug(TAG, "time: "+time);
		
		game.getGraphics().draw("Delta :"+delta, 10, 20);
		game.getGraphics().draw("Fps :"+game.getGraphics().getFramesPerSecond(), 10, 34);
		
		if(logo == null) {
			game.getActivity().error("Splash", "Cannot load image in memory");
		}
		
		//game.getGraphics().draw(logo, game.getGraphics().getWidth() - logo.getWidth(null), game.getGraphics().getHeight() - logo.getHeight(null));
		//game.getGraphics().draw(logo, 0, 0, game.getGraphics().getWidth(), game.getGraphics().getHeight(), 0, 0, 100, 100);
		
		float percent = ((float) time) / 10000;
		
		background.setXPercent(percent);
		background.setYPercent(percent);
		
		game.getGraphics().draw(background, 0, 0, game.getGraphics().getWidth(), game.getGraphics().getHeight());
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}
