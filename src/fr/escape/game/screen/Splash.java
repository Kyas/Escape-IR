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

import org.jbox2d.dynamics.BodyType;

import fr.escape.app.Input;
import fr.escape.app.Screen;
import fr.escape.game.Escape;
import fr.escape.game.entity.ships.Ship;
import fr.escape.game.entity.ships.ShipFactory;
import fr.escape.game.entity.weapons.shot.MissileShot;
import fr.escape.game.scenario.Earth;
import fr.escape.game.scenario.Stage;
import fr.escape.graphics.RepeatableScrollingTexture;
import fr.escape.graphics.ScrollingTexture;
import fr.escape.graphics.Texture;
import fr.escape.input.Gesture;

public class Splash implements Screen {

	private final static String TAG = Splash.class.getSimpleName();
	
	private final Escape game;
	private final Stage stage;
	
	private Texture logo;
	private ScrollingTexture background;
	private long time;
	private float[] gestureVal = {0,0};

	private final LinkedList<Input> events = new LinkedList<>();
	
	private final MissileShot[] msArray;
	
	//TODO remove after test
	private final ArrayList<Ship> s;
	
	public Splash(Escape game) throws IOException {
		this.game = game;
		this.time = 0;
		
		this.logo = new Texture(new File("res/Escape-IR.png"));
		this.background = new RepeatableScrollingTexture(new Texture(new File("res/04.jpg")), true);
		//this.background = new ScrollingTexture(new Texture(new File("res/04.jpg")), true);
		
		stage = new Earth();
		stage.start();
		
		msArray = new MissileShot[200];
		
		for(int i = 0; i < msArray.length; i++) {
			msArray[i] = new MissileShot();
			
			msArray[i].setPosition(game.getGraphics().getWidth()/2, game.getGraphics().getHeight()/2);
			msArray[i].setRotation(0);
			
		}
		
		//TODO remove after test
		ShipFactory sf = new ShipFactory();
		float coeff = Math.max(game.getGraphics().getWidth(),game.getGraphics().getHeight());
		s = new ArrayList<>(10);
		for(int i = 0; i < 5; i++) {
			Ship tmp = sf.createRegularShip(game.getWorld(),"NPCShip",(i *100) / coeff * 10,50 / coeff * 10,BodyType.DYNAMIC,0.5f,1);
			s.add(tmp);
		}
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
		game.getUser().getShip().setPosition(game.getWorld(),game.getGraphics(),gestureVal);

		//TODO remove after test
		float[] tmpF = {0,1.f};
		for(Ship ship : s) ship.setPosition(game.getWorld(),game.getGraphics(),tmpF);
		
		//game.getGraphics().draw("Delta: "+delta, 10, 20, Foundation.resources.getFont("visitor"), Color.WHITE);
		//game.getGraphics().draw("Fps: "+game.getGraphics().getFramesPerSecond(), 10, 34, Foundation.resources.getFont("visitor"), Color.WHITE);
		
		game.getUser().setHighscore((int) time);
		stage.update((int) (time / 1000));
		
		for(int i = 0; i < msArray.length; i++) {
			
			final MissileShot ms = msArray[i];
			final int msY = (int) percent;
			final int rX = 5;
			game.getActivity().post(new Runnable() {
				
				@Override
				public void run() {
					ms.moveBy(0, (int) msY);
					ms.rotateBy(rX);
				}
			});
			
			msArray[i].draw(game.getGraphics());
		}
	}

	@Override
	public void show() {
		game.getOverlay().show();
	}

	@Override
	public void hide() {
		game.getOverlay().hide();
	}

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
						double val = g.accept(start,events,i);
						if(val >= 0.3 && val <= 1.7) {
							gestureVal[0] = -0.5f;
							gestureVal[1] = -0.5f;
						} else if(val <= -0.3 && val >= -1.7) {
							gestureVal[0] = 0.5f;
							gestureVal[1] = -0.5f;
						} else if(val != 0) {
							gestureVal[0] = 0.f;
							gestureVal[1] = 1.5f;
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
