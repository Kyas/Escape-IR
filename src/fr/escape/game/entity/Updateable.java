package fr.escape.game.entity;

import fr.escape.app.Graphics;

/**
 * <p>
 * An interface for {@link Entity} which can update themselves without external help.
 * 
 * <p>
 * Kind of Flag for {@link Entity}
 * 
 */
public interface Updateable {

	/**
	 * <p>
	 * Update the state of the {@link Entity}.
	 * 
	 * <p>
	 * This {@link Entity} can use the given {@link Graphics} if it's a {@link Drawable}.
	 * 
	 * @param graphics {@link Graphics} which can be used for drawing this {@link Entity}.
	 * @param delta Time elapsed since the last update
	 */
	public void update(Graphics graphics, long delta);
	
}
