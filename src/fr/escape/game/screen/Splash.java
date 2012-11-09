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

import org.jbox2d.dynamics.BodyType;

import fr.escape.app.Foundation;
import fr.escape.app.Input;
import fr.escape.app.Screen;
import fr.escape.game.Escape;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.bonus.Bonus;
import fr.escape.game.entity.bonus.BonusFactory;
import fr.escape.game.entity.ships.Ship;
import fr.escape.game.entity.ships.ShipFactory;

import fr.escape.game.entity.weapons.shot.AbstractShot;
import fr.escape.game.entity.weapons.shot.Shot;
import fr.escape.game.entity.weapons.shot.ShotFactory;
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
	
	private final Escape game;
	private final Stage stage;
	
	private Texture logo;
	private ScrollingTexture background;
	private long time;
	private float[] velocity = {0,0,0};
	private boolean weaponLoaded = false;

	private final LinkedList<Input> events = new LinkedList<>();
	
	private final EntityContainer eContainer;
	
	// DEBUG
	private Shot bw;
	private boolean hit = false;
	private boolean spawn = false;
	
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
		
		this.eContainer = new EntityContainer(Math.max((int) (Foundation.GRAPHICS.getWidth() * 0.1f), (int) (Foundation.GRAPHICS.getHeight() * 0.1f)));
		
//		Shot one = ShotFactory.createMissileShot(this.eContainer);
//		one.setPosition(game.getGraphics().getWidth() / 2, 0);
//		this.eContainer.push(one);
//		
//		Shot two = ShotFactory.createShiboleetShot(this.eContainer);
//		two.setPosition(game.getGraphics().getWidth() / 2, 0);
//		this.eContainer.push(two);
		
		int x = game.getGraphics().getWidth() / 2, y = game.getGraphics().getHeight() / 2;
		Shot three = ShotFactory.createBlackholeShot(game.getWorld(),CoordinateConverter.toMeterX(x),CoordinateConverter.toMeterY(y),this.eContainer);
		three.setPosition(x,y);
		
		/*Shot three = ShotFactory.createBlackholeShot(game.getWorld(), 0, 0, eContainer);
		three.setPosition(game.getGraphics().getWidth() / 2, game.getGraphics().getHeight() / 2);*/
		this.eContainer.push(three);
		bw = three;
		
		//TODO remove after test
		ShipFactory sf = new ShipFactory();
		s = new ArrayList<>(10);
		for(int i = 0; i < 3; i++) {
			Ship tmp = sf.createRegularShip(game.getWorld(),CoordinateConverter.toMeterX(i * 100),CoordinateConverter.toMeterY(50),BodyType.DYNAMIC,0.5f,false,eContainer);
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
		game.getUser().getShip().setPosition(game.getWorld(),game.getGraphics(),velocity);

		//TODO remove after test
		float[] tmpF = {s.size(),0,0.5f};
		for(Ship ship : s) ship.setPosition(game.getWorld(),game.getGraphics(),tmpF);
		
		//game.getGraphics().draw("Delta: "+delta, 10, 20, Foundation.resources.getFont("visitor"), Color.WHITE);
		//game.getGraphics().draw("Fps: "+game.getGraphics().getFramesPerSecond(), 10, 34, Foundation.resources.getFont("visitor"), Color.WHITE);
		
		game.getUser().setHighscore((int) time);
		//stage.update((int) (time / 1000));

		game.getGraphics().draw(Shapes.createLine(0, game.getGraphics().getHeight(), game.getGraphics().getWidth(), 0), Color.CYAN);
		
		eContainer.update(game.getGraphics(), delta);
		
//		if(time > 3000) {
//			game.getUser().removeOneLife();
//		}
		
		if(time > 1000 && time <= 3000) {
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
		}
		
		if((time % 1000) > 0 && (time % 1000) < 100) {
			
			if(!spawn) {
				Bonus bonus = BonusFactory.createBonus(eContainer);
				if(bonus != null) {
					bonus.setPosition(game.getGraphics().getWidth() / 2, 0);
					eContainer.push(bonus);
				}
				spawn = true;
			}
			
		} else {
			spawn = false;
		}
		//world.step(1.0f/60.0f,6,2);
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
		int error = CoordinateConverter.toPixelX(ship.getBody().getFixtureList().getShape().m_radius);
		
		if((i.getX() > x && i.getX() < x + error) && (i.getY() > y && i.getY() < y + error)) {
			if(!weaponLoaded) {
				//TODO load weapon
				game.getUser().getShip().getActiveWeapon().load(game.getWorld(),ship.getX(),ship.getY(),eContainer);
				System.out.println("Weapon loading ...");
				weaponLoaded = true;
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
						
						if(wg.accept(start, events, i, velocity)) {
							System.out.println("Weapon Gesture Accept : Fire");
							//TODO weapon fire
						}
						
						weaponLoaded = false;
						
					} else {
						
						for(Gesture g : gestures) {
							if(g.accept(start,events,i,velocity))
								break;
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
