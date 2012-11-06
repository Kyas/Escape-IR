package fr.escape.game.entity;

import java.awt.Rectangle;

import fr.escape.app.Foundation;
import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;

public final class EntityContainer implements KillNotifier, EdgeNotifier {

	private static final String TAG = EntityContainer.class.getSimpleName();
	
	private final Rectangle edge;
	
	public EntityContainer() {
		
		int margin = Math.max((int) (Foundation.graphics.getWidth() * 0.1f), (int) (Foundation.graphics.getHeight() * 0.1f));
		this.edge = new Rectangle(-margin, -margin, Foundation.graphics.getWidth() + margin, Foundation.graphics.getHeight() + margin);
		
	}
	
	@Override
	public boolean edgeReached(Entity e) {
		Foundation.activity.debug(TAG, "Remove Entity"+e);
		return false;
	}

	@Override
	public boolean destroy(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Rectangle getEdge() {
		return edge;
	}

}
