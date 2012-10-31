package fr.escape.game;

import fr.escape.app.Foundation;
import fr.escape.ships.Ship;

public final class User implements Receiver, Sender {
	
	private Ship ship;
	private int highscore;
	private Receiver receiver;

	// TODO Ship
	public User() {
		highscore = 0;
		ship = null;
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
		Foundation.activity.debug("User", "Weapons: "+weaponID);
		//ship.setActiveWeapon(weaponID);
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
}
