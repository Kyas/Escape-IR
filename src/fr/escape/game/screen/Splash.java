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
import java.util.Objects;

import fr.escape.app.Graphics;
import fr.escape.app.Input;
import fr.escape.app.Screen;
import fr.escape.game.Escape;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.bonus.Bonus;
import fr.escape.game.entity.bonus.BonusFactory;
import fr.escape.game.entity.ships.Ship;

import fr.escape.game.entity.weapons.shot.Shot;
import fr.escape.game.scenario.Earth;
import fr.escape.game.scenario.Stage;
import fr.escape.graphics.AnimationTexture;
import fr.escape.graphics.RepeatableScrollingTexture;
import fr.escape.graphics.ScrollingTexture;
import fr.escape.graphics.Shapes;
import fr.escape.graphics.Texture;
import fr.escape.input.Gesture;
import fr.escape.input.WeaponGesture;

public class Splash implements Screen {

	private final static String TAG = Splash.class.getSimpleName();
	
	private final Escape game;
	private final Stage stage;
	
	private Texture logo;
	private ScrollingTexture background;
	private long time;
	private float[] velocity = {0, 0, 0, 0};
	private Color color = Color.WHITE;

	private final LinkedList<Input> events = new LinkedList<>();
	
	// DEBUG
	private Shot bw;
	private boolean hit = false;
	private boolean spawn = false;
	
	private Ship s2;
	private Ship s3;
	
	private AnimationTexture at;
	
	public Splash(Escape game) throws IOException {
		this.game = game;
		this.time = 0;
		
		this.logo = new Texture(new File("res/Escape-IR.png"));
		this.background = new RepeatableScrollingTexture(new Texture(new File("res/04.jpg")), true);
		//this.background = new ScrollingTexture(new Texture(new File("res/04.jpg")), true);
		
		stage = new Earth(game.getShipFactory(), game.getEntityContainer());
		stage.start();
		
//		Shot one = ShotFactory.createMissileShot(this.eContainer);
//		one.setPosition(game.getGraphics().getWidth() / 2, 0);
//		this.eContainer.push(one);
//		
//		Shot two = ShotFactory.createShiboleetShot(this.eContainer);
//		two.setPosition(game.getGraphics().getWidth() / 2, 0);
//		this.eContainer.push(two);
		
		/*int x = game.getGraphics().getWidth() / 2, y = game.getGraphics().getHeight() / 2;
		Shot three = ShotFactory.createBlackholeShot(game.getWorld(),CoordinateConverter.toMeter(x),CoordinateConverter.toMeter(y),this.eContainer);
		three.setPosition(x,y);*/
		
		/*Shot three = ShotFactory.createBlackholeShot(game.getWorld(), 0, 0, eContainer);
		three.setPosition(game.getGraphics().getWidth() / 2, game.getGraphics().getHeight() / 2);*/
		/*this.eContainer.push(three);
		bw = three;*/
		
		//TODO remove after test
        
        /*Ship s1 = game.getShipFactory().createRegularShip(CoordinateConverter.toMeterX(200),CoordinateConverter.toMeterY(0),false);
        s1.moveBy(new float[]{1000.0f,0.0f,1.5f});
        Ship s2 = game.getShipFactory().createRegularShip(CoordinateConverter.toMeterX(200),CoordinateConverter.toMeterY(600),false);
        s2.moveBy(new float[]{1000.0f,0.0f,-2.0f});
        Ship s3 = game.getShipFactory().createRegularShip(CoordinateConverter.toMeterX(0),CoordinateConverter.toMeterY(300),false);
        s3.moveBy(new float[]{1000.0f,2.0f,0.0f});
        Ship s4 = game.getShipFactory().createRegularShip(CoordinateConverter.toMeterX(400),CoordinateConverter.toMeterY(300),false);
        s4.moveBy(new float[]{1000.0f,-2.0f,0.0f});
        
        game.getEntityContainer().push(s1); game.getEntityContainer().push(s2); game.getEntityContainer().push(s3); game.getEntityContainer().push(s4);*/
        /*for(int i = 0; i < 2; i++) {
                Ship tmp = sf.createRegularShip(CoordinateConverter.toMeterX(i * 75 + 50),CoordinateConverter.toMeterY(50),false);
                tmp.getBody().setLinearVelocity(new Vec2(0.0f,1.0f));
                eContainer.push(tmp);
        }
        eContainer.push(sf.createRegularShip(CoordinateConverter.toMeterX(game.getGraphics().getWidth()/2), CoordinateConverter.toMeterY(50),false));*/

        Bonus bonus = null;
        while(bonus == null) {
                bonus = BonusFactory.createBonus(game.getWorld(),CoordinateConverter.toMeterX(game.getGraphics().getWidth()/2), CoordinateConverter.toMeterY(-50),game.getEntityContainer());
        }
        float[] bV = {0.0f,0.0f,1.0f};
        bonus.moveBy(bV);
        game.getEntityContainer().push(bonus);
	}
	
	public void drawEvents() {
		Iterator<Input> it = events.iterator();
		Input lastInput = null;
		Graphics graphics = game.getGraphics();
		
		if(it.hasNext()) {
			lastInput = it.next();
		}
		
		while(it.hasNext()) {
			Input input = it.next();
			graphics.draw(Shapes.createLine(lastInput.getX(), lastInput.getY(), input.getX(), input.getY()), color);
			lastInput = input;
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
		
		if(!game.getUser().getShip().isWeaponLoaded()) {
			drawEvents();
		}
		
		game.getEntityContainer().flush();
		game.getWorld().step(delta / 1000.0f, 6, 2);
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
		
		Ship ship = game.getUser().getShip();
		
		int x = CoordinateConverter.toPixelX(ship.getX());
		int y = CoordinateConverter.toPixelY(ship.getY());
		int errorX = ship.getEdge().width / 2;
		int errorY = ship.getEdge().height / 2;
		
		if((i.getX() > x - errorX && i.getX() < x + errorX) && (i.getY() > y - errorY && i.getY() < y + errorY)) {
			
			ship.receive(Ship.MESSAGE_EXECUTE_LEFT_LOOP);
			game.getActivity().debug(TAG, "User Click on Ship");
			
			//TODO debug
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
					
					if(touch(start)) {
						
						WeaponGesture wg = new WeaponGesture();
						Ship ship = game.getUser().getShip();
						
						float[] weaponVelocity = new float[3];
						
						if(wg.accept(start, events, i, weaponVelocity) && ship.isWeaponLoaded()) {
							game.getActivity().debug(TAG, "Weapon Gesture Accept : Fire");
							ship.fireWeapon(weaponVelocity);
						}
						
					} else {
						
						for(Gesture g : gestures) {
							if(g.accept(start,events,i,velocity)) {
								System.out.println("Gesture ok");
								color = Color.GREEN;
								break;
							}
						}
						
					}
					events.clear();
				}
				break;
			}
			default: {
				events.add(i);
			}
		}
		
		return false;
	}

}
