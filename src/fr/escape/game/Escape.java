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

package fr.escape.game;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import fr.escape.app.Game;
import fr.escape.app.Input;
import fr.escape.app.Overlay;
import fr.escape.game.User.LifeListener;
import fr.escape.game.entity.CollisionDetector;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.ships.Ship;
import fr.escape.game.entity.ships.ShipFactory;
import fr.escape.game.entity.weapons.Weapon;
import fr.escape.game.entity.weapons.Weapons;
import fr.escape.game.screen.Lost;
import fr.escape.game.screen.Menu;
import fr.escape.game.screen.Splash;
import fr.escape.game.screen.Error;
import fr.escape.game.ui.IngameUI;
import fr.escape.game.ui.UIHighscore;
import fr.escape.game.ui.UIWeapons;
import fr.escape.input.BackOff;
import fr.escape.input.Drift;
import fr.escape.input.Gesture;
import fr.escape.input.LeftLoop;
import fr.escape.input.RightLoop;

/**
 * <p>
 * Escape Game
 * 
 * <p>
 * This class is a huge Controller that link many components together.
 * 
 */
public final class Escape extends Game implements LifeListener {
	
	/**
	 * Player Model
	 */
	private final User user;
	
	/**
	 * Screen
	 */
	private Lost lost;
	private Menu menu;
	private Splash splash;
	private Error error;
	
	/**
	 * Overlay used ingame
	 */
	private IngameUI ingameUI;
	
	/**
	 * Default Constructor for the Game.
	 */
	public Escape() {
		user = new User(this);
	}
	
	/**
	 * @see Game#create()
	 */
	@Override
	public void create() {
		try {
			
			ShipFactory sf = new ShipFactory();
			
			// Create World
			World world = new World(new Vec2(0.0f,0.0f), true);
			world.setContactListener(new CollisionDetector());
			setWorld(world);
			
			// Create Screen
			lost = new Lost(this);
			menu = new Menu(this);
			splash = new Splash(this);
			// Other Screen if any ...
			
			// Create Game Components
			ingameUI = new IngameUI();

			ArrayList<Gesture> gestures = new ArrayList<>();
			
			UIHighscore uHighscore = new UIHighscore(this);
			Ship ship = sf.createRegularShip(world,CoordinateConverter.toMeter(getGraphics().getWidth()/2),CoordinateConverter.toMeter(getGraphics().getHeight() - 100),BodyType.DYNAMIC,0.5f,true,null);

			getUser().register(uHighscore);
			System.out.println("Ship set");
			getUser().setShip(ship);
			
			gestures.add(new Drift());
			gestures.add(new BackOff());
			gestures.add(new LeftLoop());
			gestures.add(new RightLoop());
			getUser().setGestures(gestures);
			getUser().setShip(ship);
			
			List<Weapon> lWeapons = Weapons.createListOfWeapons();
			
			UIWeapons uWeapons = new UIWeapons(this, getUser(), lWeapons, lWeapons.get(0));
			
			ingameUI.add(uHighscore);
			ingameUI.add(uWeapons);
			
			// Attach Start Screen
			setScreen(splash);
			
		} catch(Exception e) {
			error = new Error(this);
			getActivity().error("Escape", "Exception raised during create()", e);
			setScreen(error);
		}
		
	}
	
	/**
	 * @see Game#render()
	 */
	@Override
	public void render() {
		super.render();
		if(ingameUI != null) {
			ingameUI.render(getGraphics().getDeltaTime());
		}
	}
	
	/**
	 * @see Game#touch(Input)
	 */
	@Override
	public boolean touch(Input i) {
		// TODO REMOVE ?
		System.out.println("Touch Event");
		if(getOverlay().touch(i)) {
			return true;
		}
		return getScreen().touch(i);
	}
	
	/**
	 * @see Game#move(Input)
	 */
	@Override
	public boolean move(Input i) {
		// TODO REMOVE ?
		System.out.println("Move Event");
		if(getOverlay().move(i)) {
			return true;
		}
		return getScreen().move(i);
	}

	/**
	 * <p>
	 * Return the Overlay used as Ingame UI.
	 * 
	 * @return {@link Overlay}
	 */
	public Overlay getOverlay() {
		return ingameUI;
	}
	
	/**
	 * Retrieve the {@link User} in this Game.
	 * 
	 * @return {@link User}
	 */
	public User getUser() {
		return user;
	}

	@Override
	public void restart() {
		// TODO Finish
		setScreen(splash);
	}

	@Override
	public void stop() {
		setScreen(lost);
	}
	
	/**
	 * Update the current Screen by using Menu
	 */
	public void setMenuScreen() {
		setScreen(menu);
	}
	
}
