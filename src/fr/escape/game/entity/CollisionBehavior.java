package fr.escape.game.entity;

import fr.escape.game.User;

/**
 * <p>
 * An interface for Behavior when 
 * {@link Collisionable#collision(fr.escape.game.User, int, Entity, int)} happened.
 * 
 * <p>
 * Call by {@link Collisionable}.
 */
public interface CollisionBehavior {

	/**
	 * Apply a behavior when a collision happened.
	 * 
	 * @param user Current {@link User}.
	 * @param handler {@link Entity} which handle the collision.
	 * @param other {@link Entity} which collide with the handler.
	 * @param whois {@link Entity} type for other.
	 */
	public void applyCollision(User user, Entity handler, Entity other, int type);
	
}
