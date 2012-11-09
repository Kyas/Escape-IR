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
import java.util.Objects;

import fr.escape.app.Foundation;
import fr.escape.game.entity.ships.Ship;
import fr.escape.game.message.Receiver;
import fr.escape.game.message.Sender;
import fr.escape.input.Gesture;

// TODO Finish
public final class User implements Receiver, Sender {
	
	private static final int INITIAL_LIFE = 3;
	
	private Ship ship;
	private int highscore;
	private Receiver receiver;
	private ArrayList<Gesture> gestures;
	private int life;
	private LifeListener listener;
	
	// TODO Ship
	User(LifeListener listener) {
		this.listener = Objects.requireNonNull(listener);
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
		this.receiver = receiver;
	}

	// TODO DEBUG
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	
	// TODO DEBUG
	public Ship getShip() {
		return this.ship;
	}
	
	public ArrayList<Gesture> getGestures() {
		return gestures;
	}
	
	public void setGestures(ArrayList<Gesture> gestures) {
		this.gestures = gestures;
	}
	
	public void removeOneLife() {
		
		this.life -= 1;
		
		if(this.life <= 0) {
			listener.stop();
		} else {
			listener.restart();
		}
	}
	
	/**
	 * <p>
	 * An interface for User's Life.
	 * 
	 * <p>
	 * The Listener will receive restart and stop notification depending
	 * on how many lives are left.
	 */
	// TODO Check English Grammar
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
