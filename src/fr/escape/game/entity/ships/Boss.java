package fr.escape.game.entity.ships;

/**
 * <p>
 * An interface for {@link Ship} who are the Boss at the end of the Stage.
 *
 */
public interface Boss extends Ship {
	
	/**
	 * Perform a Special Action/Fire.
	 */
	public void special();
	
	/**
	 * Perform a Movement Action
	 */
	public void move();
	
	/**
	 * Perform a Simple Fire
	 */
	public void fire();
	
}
