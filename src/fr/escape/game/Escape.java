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

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import fr.escape.app.Game;
import fr.escape.app.Input;
import fr.escape.app.Overlay;
import fr.escape.game.User.LifeListener;
import fr.escape.game.entity.CollisionDetector;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.ships.ShipFactory;
import fr.escape.game.entity.weapons.shot.ShotFactory;
import fr.escape.game.screen.Lost;
import fr.escape.game.screen.Menu;
import fr.escape.game.screen.Splash;
import fr.escape.game.screen.Error;
import fr.escape.game.screen.Victory;
import fr.escape.game.ui.IngameUI;
import fr.escape.game.ui.UIHighscore;
import fr.escape.game.ui.UIWeapons;
import fr.escape.input.Booster;
import fr.escape.input.Drift;
import fr.escape.input.Gesture;
import fr.escape.input.LeftLoop;
import fr.escape.input.RightLoop;
import fr.escape.input.Slide;

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
	private Victory victory;
	private Lost lost;
	private Menu menu;
	private Splash splash;
	private Error error;
	
	/**
	 * Overlay used ingame
	 */
	private IngameUI ingameUI;
	
	/**
	 * Game Factory
	 */
	private ShipFactory shipFactory;
	private ShotFactory shotFactory;
	
	/**
	 * Game Entity Container
	 */
	private EntityContainer entityContainer;
	
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
			
			// Create World
			World world = new World(new Vec2(0.0f,0.0f), true);
			world.setContactListener(new CollisionDetector(getUser()));
			setWorld(world);
			
			// Create Entity Container
			entityContainer = new EntityContainer(getWorld(), Math.max((int) (getGraphics().getWidth() * 0.1f), 
					(int) (getGraphics().getHeight() * 0.1f)));
			
			// Create Game Components
			ingameUI = new IngameUI();
			shotFactory = new ShotFactory(getWorld(), getEntityContainer());
			shipFactory = new ShipFactory(getWorld(), getEntityContainer(), getShotFactory());
			
			// Create Ship
			createPlayerShip();
			
			// Create Gesture
			createGestures();
			
			// Create Overlay
			createOverlay();
			
			// Create Screen
			lost = new Lost(this);
			menu = new Menu(this);
			splash = new Splash(this);
			victory = new Victory(this);
			// Other Screen if any ...
			
			// Show Entry Screen
			setScreen(lost);
			
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
		setScreen(getScreen());
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
	
	/**
	 * Update the current Screen by starting a New Game
	 */
	public void setNewGameScreen() {
		// TODO Change it by Earth
		setScreen(splash);
	}
	
	public void setVictoryScreen() {
		setScreen(victory);
	}
	
	public void setLostScreen() {
		setScreen(lost);
	}
	
	/**
	 * Get the {@link ShipFactory}
	 * 
	 * @return {@link ShipFactory}
	 */
	public ShipFactory getShipFactory() {
		return shipFactory;
	}
	
	/**
	 * Get the {@link ShotFactory}
	 * 
	 * @return {@link ShotFactory}
	 */
	public ShotFactory getShotFactory() {
		return shotFactory;
	}

	/**
	 * Get the {@link EntityContainer}
	 * 
	 * @return {@link EntityContainer}
	 */
	public EntityContainer getEntityContainer() {
		return entityContainer;
	}
	
	private void createPlayerShip() {
		getUser().setShip(getShipFactory().createRegularShip(
				CoordinateConverter.toMeterX(getGraphics().getWidth() / 2), 
				CoordinateConverter.toMeterY(getGraphics().getHeight() - 100),
				true
		));
	}
	
	private void createGestures() {
		
		ArrayList<Gesture> gestures = new ArrayList<>();
		
		gestures.add(new Drift());
		gestures.add(new Slide());
		gestures.add(new Booster());
		gestures.add(new LeftLoop());
		gestures.add(new RightLoop());
		
		getUser().setGestures(gestures);
	}
	
	private void createOverlay() {
		
		UIHighscore uHighscore = new UIHighscore(this);
		UIWeapons uWeapons = new UIWeapons(this, getUser(), getUser().getAllWeapons(), getUser().getActiveWeapon());
		
		ingameUI.add(uHighscore);
		ingameUI.add(uWeapons);
		
		getUser().register(uHighscore);
	}
}
