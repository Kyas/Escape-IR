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
import java.rmi.activation.UnknownObjectException;
import java.util.LinkedHashSet;
import java.util.Objects;

import org.jbox2d.dynamics.Body;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;

/**
 * <p>
 * A composite which handle large bunch of {@link Entity}, a container for {@link Entity} in the World.
 * 
 * <p>
 * This Container implements {@link KillNotifier} and {@link EdgeNotifier} for removing
 * {@link Entity}.
 * 
 */
public final class EntityContainer implements Entity, KillNotifier, EdgeNotifier {

	private static final String TAG = EntityContainer.class.getSimpleName();
	
	private final Rectangle edge;
	private final LinkedHashSet<Entity> entities;
	
	/**
	 * Default Constructor
	 * 
	 * @param margin Margin used for Edge World
	 */
	public EntityContainer(int margin) {
		
		this.edge = new Rectangle(-margin, -margin, Foundation.GRAPHICS.getWidth() + margin, Foundation.GRAPHICS.getHeight() + margin);
		this.entities = new LinkedHashSet<>();
		
		Foundation.ACTIVITY.debug(TAG, "EntityContainer created");
		
	}
	
	/**
	 * Push an {@link Entity} in the Container.
	 * 
	 * @param e Entity
	 * @return True if successful
	 */
	public boolean push(Entity e) {
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
		e.getBody().setActive(false);
		return this.entities.remove(e);
	}
	
	@Override
	public boolean edgeReached(Entity e) {
		return remove(Objects.requireNonNull(e));
	}

	@Override
	public boolean destroy(Entity e) {
		return remove(Objects.requireNonNull(e));
	}
	
	@Override
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

	@Override
	public Body getBody() {
		throw new UnsupportedOperationException();
	}

}
