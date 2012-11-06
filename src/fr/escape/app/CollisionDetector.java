package fr.escape.app;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

public class CollisionDetector implements ContactListener {

	@Override
	public void beginContact(Contact arg0) {
		Object dataA = arg0.getFixtureA().getBody().getUserData();
		Object dataB = arg0.getFixtureB().getBody().getUserData();
		
		switch(dataA.toString()) {
			case "PlayerShip" : System.out.print("Collision between Player and "); break;
			case "NPCShip" : System.out.print("Collision between NPC and "); break;
			default : System.out.print("Collision between another Object and "); break;
		}
		
		switch(dataB.toString()) {
			case "PlayerShip" : System.out.println("Player."); break;
			case "NPCShip" : System.out.println("NPC."); break;
			default : System.out.println("another Object."); break;
		}
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
