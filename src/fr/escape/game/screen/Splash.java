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
import fr.escape.app.Graphics;
import fr.escape.app.Input;
import fr.escape.app.Screen;
import fr.escape.game.Escape;
import fr.escape.graphics.RepeatableScrollingTexture;
import fr.escape.graphics.ScrollingTexture;
import fr.escape.graphics.Texture;
import fr.escape.input.Gesture;
import fr.escape.ships.Ship;

public class Splash implements Screen {

	private final static String TAG = Splash.class.getSimpleName();
	
	private final Escape game;
	private Texture logo;
	private ScrollingTexture background;
	private long time;
	private final LinkedList<Input> events = new LinkedList<>();
	
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
		//game.getGraphics().draw(game.getResources().getDrawable("wfireball"),game.getGraphics().getWidth()/2 - 20,game.getGraphics().getHeight() - 100);
		/*Ship ship = game.getUser().getShip();
		ship.setPosition(game.getWorld(),game.getGraphics(),Math.max(game.getGraphics().getHeight(),game.getGraphics().getWidth()));*/
		//ship.draw(game.getGraphics(),(int)ship.getBody().getPosition().x - 0.1f,(int)ship.getBody().getPosition().y - 0.1f);
		
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
		LinkedList<Input> events = this.events;
		ArrayList<Gesture> gestures = game.getUser().getGestures();
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
									Graphics graphics = game.getGraphics();
									game.getUser().getShip().setPosition(game.getWorld(),graphics,Math.max(graphics.getWidth(),graphics.getHeight()));
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
