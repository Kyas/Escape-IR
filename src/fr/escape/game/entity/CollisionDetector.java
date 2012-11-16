package fr.escape.game.entity;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import fr.escape.game.User;

// TODO Comment
public final class CollisionDetector implements ContactListener {
	
	private final User user;
	
	public CollisionDetector(User user) {
		this.user = user;
	}
	
	@Override
	public void beginContact(Contact arg0) {
		int typeA = arg0.getFixtureA().getFilterData().categoryBits;
		int typeB = arg0.getFixtureB().getFilterData().categoryBits;
		
		if(typeA != Collisionable.WALL_TYPE && typeB != Collisionable.WALL_TYPE) {
			Entity entityA = (Entity) arg0.getFixtureA().getBody().getUserData();
			Entity entityB = (Entity) arg0.getFixtureB().getBody().getUserData();
			
			entityA.collision(user, entityB, typeB);
		}
	}

	@Override
	public void endContact(Contact arg0) {}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {}

}
