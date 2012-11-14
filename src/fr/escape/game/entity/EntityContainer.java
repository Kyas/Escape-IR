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

package fr.escape.game.entity;

import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Objects;

import org.jbox2d.dynamics.World;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.bonus.Bonus;
import fr.escape.game.entity.bonus.BonusFactory;
import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;
import fr.escape.game.entity.ships.Ship;

/**
 * <p>
 * A composite which handle large bunch of {@link Entity}, a container for {@link Entity} in the World.
 * 
 * <p>
 * This Container implements {@link KillNotifier} and {@link EdgeNotifier} for removing
 * {@link Entity}.
 * 
 */
// TODO Comment
public final class EntityContainer implements Updateable, KillNotifier, EdgeNotifier {

	private static final String TAG = EntityContainer.class.getSimpleName();
	
	private final World world;
	private final Rectangle edge;
	private final LinkedHashSet<Entity> entities;
	private final LinkedList<Entity> destroyed;
	
	/**
	 * Default Constructor
	 * 
	 * @param world World used
	 * @param margin Margin used for Edge World
	 */
	public EntityContainer(World world, int margin) {
		
		this.world = world;
		this.edge = new Rectangle(-margin, -margin, Foundation.GRAPHICS.getWidth() + margin, Foundation.GRAPHICS.getHeight() + margin);
		this.entities = new LinkedHashSet<>();
		this.destroyed = new LinkedList<>();
		
		Foundation.ACTIVITY.debug(TAG, "EntityContainer created");
		
	}
	
	/**
	 * Push an {@link Entity} in the Container.
	 * 
	 * @param e Entity
	 * @return True if successful
	 */
	public boolean push(Entity e) {
		System.err.println("PUSH : " + e);
		Objects.requireNonNull(e);
		return this.entities.add(e);
	}
	
	/**
	 * Remove an {@link Entity} in the Container.
	 * 
	 * @param e Entity
	 * @return True if successful
	 */
	private boolean remove(Entity e) {
		//e.getBody().setActive(false);
		return this.entities.remove(e);
	}
	
	@Override
	public boolean edgeReached(Entity e) {
		System.err.print("Edge: "+e);
		toDestroy(Objects.requireNonNull(e));
		return true;
	}

	@Override
	public boolean destroy(Entity e) {
		System.err.println("destroy: "+e);
		toDestroy(Objects.requireNonNull(e));
		return true;
	}
	
	public void update(Graphics graphics, long delta) {
		for(Entity e : entities) {
			e.update(graphics, delta);
		}
	}

	/**
	 * Get the number of {@link Entity} in this Container
	 * 
	 * @return Number of Entity
	 */
	public int size() {
		return entities.size();
	}

	@Override
	public boolean isInside(Rectangle edge) {
		return this.edge.intersects(Objects.requireNonNull(edge));
	}

	public void toDestroy(Entity e) {
		Foundation.ACTIVITY.debug(TAG, "Add Entity in Removing Queue: "+e);
		this.destroyed.add(e);
	}
	
	public boolean flush() {
		for(Entity e : destroyed) {
			Foundation.ACTIVITY.debug(TAG, "Remove Entity: "+e+" "+((remove(e)?"[DONE]":"[FAIL]")));
			if(e.getBody() != null) {
				world.destroyBody(e.getBody());
				e.setBody(null);
			}
		}
		destroyed.clear();
		
		return true;
	}

	public boolean reset() {
		System.out.println("Nombre de body avant reset : " + world.getBodyCount());
		
		if(!flush()) {
			return false;
		}
		
		Iterator<Entity> it = entities.iterator();
		
		while(it.hasNext()) {
			Entity e = it.next();
			System.out.println("RESET : " + e);
			it.remove();
			world.destroyBody(e.getBody());
			e.setBody(null);
		}
		System.out.println("Nombre de body apres reset : " + world.getBodyCount());
		return true;
	}
	
	/**
	 * Return true if this container contains the specified {@link Entity}
	 *  
	 * @param e Entity
	 * @return True if this container contains the given {@link Entity}
	 */
	public boolean contains(Entity e) {
		return entities.contains(e);
	}
	
	/**
	 * Push a Bonus in World
	 * 
	 * @param x Bonus X Position
	 * @param y Bonus Y Position
	 * @return True if Successful.
	 */
	public boolean pushBonus(float x, float y) {
		
		Bonus bonus = BonusFactory.createBonus(world, x, y, this);
		
		
		if(bonus != null) {
			System.out.println("Bonus : " + bonus.getBody().getLinearVelocity().y);
			return push(bonus);
		}
		
		return true;
	}
	
	public boolean pushShip(Ship ship) {
		Objects.requireNonNull(ship).createBody(world);
		return push(ship);
	}

}
