package fr.escape.game.entity;

import java.awt.Rectangle;
import java.util.LinkedHashSet;
import java.util.Objects;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;

public final class EntityContainer implements KillNotifier, EdgeNotifier {

	private static final String TAG = EntityContainer.class.getSimpleName();
	
	private final Rectangle edge;
	private final LinkedHashSet<Entity> entities;
	
	public EntityContainer() {
		
		int margin = Math.max((int) (Foundation.GRAPHICS.getWidth() * 0.1f), (int) (Foundation.GRAPHICS.getHeight() * 0.1f));
		this.edge = new Rectangle(-margin, -margin, Foundation.GRAPHICS.getWidth() + margin, Foundation.GRAPHICS.getHeight() + margin);
		this.entities = new LinkedHashSet<>();
		
	}
	
	public boolean push(Entity e) {
		Objects.requireNonNull(e);
		return this.entities.add(e);
	}
	
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
	public Rectangle getEdge() {
		return edge;
	}
	
	public void update(Graphics graphics, long delta) {
		for(Entity e : entities) {
			e.update(graphics, delta);
		}
	}

	public int size() {
		return entities.size();
	}

}
