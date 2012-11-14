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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.jbox2d.common.Vec2;

import fr.escape.app.Foundation;
import fr.escape.app.Input;
import fr.escape.app.Screen;
import fr.escape.game.Escape;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.ships.Ship;

import fr.escape.game.scenario.Earth;
import fr.escape.game.scenario.Stage;
import fr.escape.graphics.RepeatableScrollingTexture;
import fr.escape.graphics.ScrollingTexture;
import fr.escape.graphics.Shapes;
import fr.escape.graphics.Texture;
import fr.escape.input.Gesture;
import fr.escape.input.WeaponGesture;
import fr.escape.resources.texture.TextureLoader;

public class Splash implements Screen {

	private final static String TAG = Splash.class.getSimpleName();
	
	private final static int MAX_ACTIVE_EVENT_TIME = 2000;
	private final static long STAGE_TIME = 7500;
	private final static long STAR_SPEED = 5000;
	
	private final Escape game;
	private final Stage stage;
	private final ScrollingTexture background;
	private final ScrollingTexture star;
	private final LinkedList<Input> events;
	
	private long time;
	private float[] velocity = {0, 0, 0, 0};

	private List<Input> activeEvents;
	private long activeEventTime;
	
	private boolean spawnBoss;
	
	public Splash(Escape game) throws IOException {
		
		this.game = game;
		this.background = new ScrollingTexture(game.getResources().getTexture(TextureLoader.BACKGROUND_JUPITER), true);
		this.star = new RepeatableScrollingTexture(game.getResources().getTexture(TextureLoader.OVERLAY_STAR), true);
		this.stage = new Earth(game.getShipFactory(), game.getEntityContainer());
        this.events = new LinkedList<>();
        this.spawnBoss = false;
        
	}
	
	@Override
	public void render(long delta) {

		time += delta;
		activeEventTime += delta;
		
		float percent = ((float) time) / STAGE_TIME;

		if(percent >= 1.0f) {

			if(spawnBoss == false) {
				spawnBoss = true;
				
				// TODO Spawn the Boss
				System.err.println("Spawn du boss");
			}
			
			percent = 1.0f;
		}
		
		background.setYPercent(percent);
		game.getGraphics().draw(background, 0, 0, game.getGraphics().getWidth(), game.getGraphics().getHeight());
		
		percent = ((float) time) / STAR_SPEED;
		
		if(!spawnBoss) {
			star.setYPercent(percent);
			game.getGraphics().draw(star, 0, 0, game.getGraphics().getWidth(), game.getGraphics().getHeight());
		}
		
		game.getUser().getShip().update(game.getGraphics(), delta);
		game.getUser().getShip().moveBy(velocity);
		
		stage.update((int) (time / 1000));
		game.getEntityContainer().update(game.getGraphics(), delta);
		
		if(!events.isEmpty()) {
			activeEvents = Screens.drawEventsOnScreen(game.getGraphics(), events, Color.WHITE);
			activeEventTime = 0;
		}
		
		if(events.isEmpty() && activeEvents != null) {
			Screens.drawEventsOnScreen(game.getGraphics(), activeEvents, Color.GREEN);
		}
		
		if(activeEventTime > MAX_ACTIVE_EVENT_TIME) {
			activeEvents = null;
		}
		
		game.getEntityContainer().flush();
		
	}

	@Override
	public void show() {
		
		game.getOverlay().show();
		game.getEntityContainer().reset();
		
		time = 0;
		stage.start();
		
		if(activeEvents != null) {
			activeEvents.clear();
		}
		activeEventTime = 0;
		events.clear();
	}

	@Override
	public void hide() {
		
		float x = CoordinateConverter.toMeterX(game.getGraphics().getWidth() / 2);
		float y = CoordinateConverter.toMeterY(game.getGraphics().getHeight() - 100);
		
		game.getOverlay().hide();
		game.getEntityContainer().reset();
		
		Ship userShip = game.getUser().getShip();
		
		userShip.getActiveWeapon().unload();
		userShip.getBody().setTransform(new Vec2(x, y), userShip.getBody().getAngle());
		
		stage.reset();
	}

	@Override
	public boolean touch(Input i) {
		
		Ship ship = game.getUser().getShip();
		
		int x = CoordinateConverter.toPixelX(ship.getX());
		int y = CoordinateConverter.toPixelY(ship.getY());
		int errorX = ship.getEdge().width / 2;
		int errorY = ship.getEdge().height / 2;
		
		if((i.getX() > x - errorX && i.getX() < x + errorX) && (i.getY() > y - errorY && i.getY() < y + errorY)) {
			
			if(ship.loadWeapon()) {
				game.getActivity().debug(TAG, "Weapon Gesture Accept : Load");
			}
			
			return true;
		}
		
		return false;
	}

	@Override
	public boolean move(final Input i) {
		
		Objects.requireNonNull(i);
		LinkedList<Input> events = this.events;
		ArrayList<Gesture> gestures = game.getUser().getGestures();
		
		switch(i.getKind()) {
			case ACTION_UP: {
				
				Iterator<Input> it = events.iterator();
				
				if(it.hasNext()) {
					
					Input start = it.next(); it.remove();
					boolean accept = false;
					
					if(touch(start)) {
						
						WeaponGesture wg = new WeaponGesture();
						Ship ship = game.getUser().getShip();
						
						float[] weaponVelocity = new float[3];
						
						if(wg.accept(start, events, i, weaponVelocity) && ship.isWeaponLoaded()) {
							game.getActivity().debug(TAG, "Weapon Gesture Accept : Fire");
							ship.fireWeapon(weaponVelocity);
							accept = true;
						}
						
					} else {
						
						for(Gesture g : gestures) {
							if(g.accept(start, events, i, velocity)) {
								accept = true;
								break;
							}
						}
						
					}
					
					if(!accept) {
						activeEvents = null;
					}
					events.clear();
					
				}
				break;
			}
			default: {
				activeEvents = null;
				events.add(i);
			}
		}
		
		return false;
	}

}
