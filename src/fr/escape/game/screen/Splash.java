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

public class Splash implements Screen {

	private final static String TAG = Splash.class.getSimpleName();
	
	private final static int MAX_ACTIVE_EVENT_TIME = 2000;
	
	private final Escape game;
	private final Stage stage;
	private final ScrollingTexture background;
	private final LinkedList<Input> events;
	
	private long time;
	private float[] velocity = {0, 0, 0, 0};

	private List<Input> activeEvents;
	private long activeEventTime;
	
	public Splash(Escape game) throws IOException {
		
		this.game = game;
		this.background = new RepeatableScrollingTexture(new Texture(new File("res/04.jpg")), true);
		this.stage = new Earth(game.getShipFactory(), game.getEntityContainer());
        this.events = new LinkedList<>();
        
	}
	
	@Override
	public void render(long delta) {

		time += delta;
		activeEventTime += delta;
		
		float percent = ((float) time) / 10000;
		
		//background.setXPercent(percent);
		background.setYPercent(percent);
		
		game.getGraphics().draw(background, 0, 0, game.getGraphics().getWidth(), game.getGraphics().getHeight());
		
		game.getUser().getShip().update(game.getGraphics(), delta);
		
		// TODO THOMAS ?
		game.getUser().getShip().moveBy(velocity);

		//game.getGraphics().draw("Delta: "+delta, 10, 20, Foundation.resources.getFont("visitor"), Color.WHITE);
		//game.getGraphics().draw("Fps: "+game.getGraphics().getFramesPerSecond(), 10, 34, Foundation.resources.getFont("visitor"), Color.WHITE);
		
		game.getUser().setHighscore((int) time);
		stage.update((int) (time / 1000));

		game.getGraphics().draw(Shapes.createLine(0, game.getGraphics().getHeight(), game.getGraphics().getWidth(), 0), Color.CYAN);
		
		game.getEntityContainer().update(game.getGraphics(), delta);
		
//		if(time > 3000) {
//			game.getUser().removeOneLife();
//		}
		
		/*if(time > 1000 && time <= 3000) {
			bw.receive(AbstractShot.MESSAGE_LOAD);
		} else if(time > 3000 && time <= 5000) {
			bw.receive(AbstractShot.MESSAGE_FIRE);
			bw.receive(AbstractShot.MESSAGE_CRUISE);
		} else if(time > 5000 && time <= 7000 && !hit) {
			bw.receive(AbstractShot.MESSAGE_HIT);
			hit = true;
		} else if(time > 13000){
			if(bw != null) {
				bw.receive(AbstractShot.MESSAGE_DESTROY);
				bw = null;
			}
		}*/
		
//		if((time % 1000) > 0 && (time % 1000) < 100) {
//			
//			if(!spawn) {
//				Bonus bonus = BonusFactory.createBonus(eContainer);
//				if(bonus != null) {
//					bonus.setPosition(game.getGraphics().getWidth() / 2, 0);
//					eContainer.push(bonus);
//				}
//				spawn = true;
//			}
//			
//		} else {
//			spawn = false;
//		}

		if((time % 1000) > 0 && (time % 1000) < 100) {
			
			//s2.fireWeapon(world, ec, velocity);
			
		}
		
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
		Foundation.ACTIVITY.debug(TAG, "Show");
		game.getOverlay().show();
		game.getEntityContainer().reset();
		time = 0;
		stage.start();
	}

	@Override
	public void hide() {
		Foundation.ACTIVITY.debug(TAG, "Hide");
		game.getOverlay().hide();
		game.getEntityContainer().reset();
		game.getUser().getShip().getActiveWeapon().unload();
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
