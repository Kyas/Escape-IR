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
import java.util.Objects;

import fr.escape.app.Foundation;
import fr.escape.game.entity.ships.Ship;
import fr.escape.game.entity.weapons.Weapon;
import fr.escape.game.message.Receiver;
import fr.escape.game.message.Sender;
import fr.escape.input.Gesture;

// TODO Finish
public final class User implements Receiver, Sender {
	
	private static final String TAG = User.class.getSimpleName();
	private static final int INITIAL_LIFE = 3;
	
	private final Escape game;
	private final Runnable restart;
	private final Runnable stop;
	
	private Ship ship;
	private int highscore;
	private Receiver receiver;
	private ArrayList<Gesture> gestures;
	private int life;
	
	User(Escape game) {
		this.game = Objects.requireNonNull(game);
		this.restart = new Runnable() {
			
			@Override
			public void run() {
				getLifeListener().restart();
			}
			
		};
		this.stop = new Runnable() {
			
			@Override
			public void run() {
				getLifeListener().stop();
			}
			
		};
		this.highscore = 0;
		this.ship = null;
		this.life = INITIAL_LIFE;
	}

	public int getHighscore() {
		return highscore;
	}
	
	public void setHighscore(int highscore) {
		this.highscore = highscore;
		this.send(highscore);
	}
	
	public void addScore(final int score) {
		Foundation.ACTIVITY.log(TAG, "Add "+score+" to Highscore");
		Foundation.ACTIVITY.post(new Runnable() {
			
			@Override
			public void run() {
				setHighscore(getHighscore() + score);
			}
			
		});
	}
	
	public boolean reset(float x, float y) {
		Foundation.ACTIVITY.log(TAG, "User: Reset Requested");
		this.highscore = 0;
		this.life = INITIAL_LIFE;
		if(!this.ship.reset(x,y)) {
			throw new IllegalStateException();
		}
		return true;
	}

	/**
	 * <p>
	 * Send Highscore for a Receiver.
	 * 
	 * <p>
	 * This method DOES NOT update the Highscore, 
	 * use {@link User#setHighscore(int)} instead.
	 * 
	 * @param highscore Highscore
	 */
	@Override
	public void send(int highscore) {
		if(receiver == null) {
			throw new AssertionError();
		}
		receiver.receive(highscore);
	}

	/**
	 * <p>
	 * Receive Weapon Selection from a {@link Sender} and 
	 * change the active weapon on {@link User#ship}.
	 * 
	 * @param weaponID Weapon ID
	 * @see Ship#setActiveWeapon(int)
	 */
	@Override
	public void receive(int weaponID) {
		Foundation.ACTIVITY.debug("User", "Weapons: "+weaponID);
		assert ship != null;
		ship.setActiveWeapon(weaponID);
	}

	/**
	 * @see {@link Sender#register(Receiver)}
	 */
	@Override
	public void register(Receiver receiver) {
		this.receiver = Objects.requireNonNull(receiver);
	}

	// TODO DEBUG
	public void setShip(Ship ship) {
		this.ship = Objects.requireNonNull(ship);
	}
	
	// TODO DEBUG
	public Ship getShip() {
		return this.ship;
	}
	
	public ArrayList<Gesture> getGestures() {
		return gestures;
	}
	
	public void setGestures(ArrayList<Gesture> gestures) {
		this.gestures = Objects.requireNonNull(gestures);
	}
	
	public int getLife() {
		return life;
	}
	
	public void removeOneLife() {
		
		Foundation.ACTIVITY.debug(TAG, "Remove One Life for User");
		this.life -= 1;
		
		if(this.life <= 0) {
			Foundation.ACTIVITY.post(stop);
		} else {
			Foundation.ACTIVITY.post(restart);
		}
	}
	
	public void addBonus(int weapon, int number) {
		Foundation.ACTIVITY.debug(TAG, "Bonus loaded in Player {"+weapon+", "+number+"}");
		getShip().reloadWeapon(weapon, number);
	}
	
	public List<Weapon> getAllWeapons() {
		return Objects.requireNonNull(getShip(), "getShip() is empty").getAllWeapons();
	}

	public Weapon getActiveWeapon() {
		return Objects.requireNonNull(getShip(), "getShip() is empty").getActiveWeapon();
	}
	
	public LifeListener getLifeListener() {
		return game;
	}
	
	/**
	 * <p>
	 * An interface for User's Life.
	 * 
	 * <p>
	 * The Listener will receive restart and stop notification depending
	 * on how many lives are left.
	 */
	public static interface LifeListener {
		
		/**
		 * User lose but has remaining lives.
		 */
		public void restart();
		
		/**
		 * User lose without extra lives.
		 */
		public void stop();
		
	}
	
}
