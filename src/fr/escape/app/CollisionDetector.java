package fr.escape.app;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

public class CollisionDetector implements ContactListener {

	@Override
	public void beginContact(Contact arg0) {		
		Body a = arg0.getFixtureA().getBody();
		Body b = arg0.getFixtureB().getBody();
		
		if(a.getUserData().equals("PlayerShip") || b.getUserData().equals("PlayerShip")) {
			//TODO player lost a life
			System.out.println("Player lost a life");
		}
		
		a.setActive(false);
		b.setActive(false);
	}

	@Override
	public void endContact(Contact arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
		
	}

}
