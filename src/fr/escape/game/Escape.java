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
import fr.escape.game.screen.Splash;
import fr.escape.game.screen.Error;
import fr.escape.game.ui.IngameUI;
import fr.escape.game.ui.UIHighscore;
import fr.escape.game.ui.UIWeapons;
import fr.escape.ships.Ship;
import fr.escape.ships.ShipFactory;
import fr.escape.weapons.BlackHole;
import fr.escape.weapons.Weapon;
import fr.escape.weapons.Weapons;
import fr.escape.input.BackOff;
import fr.escape.input.Drift;
import fr.escape.input.Gesture;
import fr.escape.input.LeftLoop;
import fr.escape.input.RightLoop;

public class Escape extends Game {
	private Splash splash;
	private Error error;
	private IngameUI ingameUI;
	
	/**
	 * @see Game#create()
	 */
	@Override
	public void create() {
		try {
			float coeff = Math.max(getGraphics().getWidth(),getGraphics().getHeight());
			ShipFactory sf = new ShipFactory();
			Vec2 gravity = new Vec2(0.0f,0.0f);
			World world = new World(gravity,true);
			setWorld(world);
			// Create Screen
			splash = new Splash(this);
			// Other Screen if any ...
			
			// Create Game Components
			ingameUI = new IngameUI();

			ArrayList<Gesture> gestures = new ArrayList<>();
			
			UIHighscore uHighscore = new UIHighscore(this);
			Ship ship = sf.createRegularShip(world,(getGraphics().getWidth()/2 - 20) / coeff * 10,(getGraphics().getHeight() - 50) / coeff * 10,BodyType.DYNAMIC,1);
			getUser().register(uHighscore);
			getUser().setShip(ship);
			
			gestures.add(new Drift());
			gestures.add(new BackOff());
			gestures.add(new LeftLoop());
			gestures.add(new RightLoop());
			getUser().setGestures(gestures);
			getUser().setShip(ship);
			List<Weapon> lWeapons = new ArrayList<>();
			
			Weapon w = new BlackHole();
			Weapons.validate(w);
			
			lWeapons.add(w);
			lWeapons.add(w);
			lWeapons.add(w);
			lWeapons.add(w);
			
			UIWeapons uWeapons = new UIWeapons(this, getUser(), lWeapons);
			
			ingameUI.add(uHighscore);
			ingameUI.add(uWeapons);
			
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
	
}
