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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

import fr.escape.app.Foundation;
import fr.escape.app.Input;
import fr.escape.app.Screen;
import fr.escape.game.Escape;
import fr.escape.graphics.RepeatableScrollingTexture;
import fr.escape.graphics.ScrollingTexture;
import fr.escape.graphics.Texture;
import fr.escape.input.Gesture;

public class Splash implements Screen {

	private final static String TAG = Splash.class.getSimpleName();
	
	private final Escape game;
	private Texture logo;
	private ScrollingTexture background;
	private long time;
	
	public Splash(Escape game) throws IOException {
		
		this.game = game;
		this.time = 0;
		
		this.logo = new Texture(new File("res/Escape-IR.png"));
		this.background = new RepeatableScrollingTexture(new Texture(new File("res/04.jpg")), true);
		//this.background = new ScrollingTexture(new Texture(new File("res/04.jpg")), true);
		
	}
	
	@Override
	public void render(long delta) {

		time += delta;
		
		if(logo == null) {
			game.getActivity().error(TAG, "Cannot load image in memory");
		}
		
		float percent = ((float) time) / 10000;
		
		//background.setXPercent(percent);
		background.setYPercent(percent);
		
		game.getGraphics().draw(background, 0, 0, game.getGraphics().getWidth(), game.getGraphics().getHeight());
		
		game.getShip().setPosition(180,500);
		
		//game.getGraphics().draw("Delta: "+delta, 10, 20, Foundation.resources.getFont("visitor"), Color.WHITE);
		//game.getGraphics().draw("Fps: "+game.getGraphics().getFramesPerSecond(), 10, 34, Foundation.resources.getFont("visitor"), Color.WHITE);
		
		game.getUser().setHighscore((int) time);
	}

	@Override
	public void show() {
		game.getOverlay().show();
	}

	@Override
	public void hide() {}

	@Override
	public boolean touch(Input i) {
		return false;
	}

	@Override
	public boolean move(final Input i) {
		Objects.requireNonNull(i);
		LinkedList<Input> events = game.getEvents();
		ArrayList<Gesture> gestures = game.getGestures();
		switch(i.getKind().name()) {
			case "ACTION_UP" :
				Iterator<Input> it = events.iterator();
				if(it.hasNext()) {
					Input start = it.next(); it.remove();
					for(Gesture g : gestures) {
						if(g.accept(start,events,i)) {
							System.out.println(i.getX() + " " + i.getY());
							Foundation.activity.post(new Runnable() {
								@Override
								public void run() {
									game.getShip().setPosition(i.getX(),i.getY());
								}
							});
						}
					}
					events.clear();
				}
				break;
			default :
				events.add(i);
		}
		return false;
	}

}
