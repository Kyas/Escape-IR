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
	
	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	/**
	 * <p>
	 * Send Highscore for Receiver.
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
	 * Receive Weapon Selection.
	 * 
	 * @param weaponID Weapon ID
	 */
	@Override
	public void receive(int weaponID) {
		ship.setActiveWeapon(weaponID);
	}
	
}
