package fr.escape.game.entity.notifier;

import java.awt.Rectangle;

import fr.escape.game.entity.Entity;

public interface EdgeNotifier {
	
	public boolean edgeReached(Entity e);
	public Rectangle getEdge();
	
}
