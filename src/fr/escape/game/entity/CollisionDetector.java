package fr.escape.game.entity;

import java.util.ArrayList;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;

import fr.escape.game.entity.weapons.shot.AbstractShot;
import fr.escape.game.entity.weapons.shot.Shot;

// TODO Comment
public final class CollisionDetector implements ContactListener {

	@Override
	@SuppressWarnings("unchecked")
	public void beginContact(Contact arg0) {
		System.out.println("2");
		Body a = arg0.getFixtureA().getBody();
		Body b = arg0.getFixtureB().getBody();
		
		ArrayList<Object> lA = (ArrayList<Object>) a.getUserData();
		ArrayList<Object> lB = (ArrayList<Object>) b.getUserData();
		Entity e1 = (Entity) lA.get(1);
		Entity e2 = (Entity) lB.get(1);
		
		String oaType = (String) lA.get(0);
		String obType = (String) lB.get(0);
		
		switch (oaType) {
			case "PlayerShip" :
				System.out.println("Player lost a life");
				e1.toDestroy();
				break;
			case "Ship" : 
				System.out.println("Ship destroy");
				e1.toDestroy();
				break;
			case "Shot" : 
				System.out.println("Shot Hit");
				Shot shot = (Shot) e1;
				shot.receive(AbstractShot.MESSAGE_HIT);
				break;
			case "Bonus" :
				System.out.println("Bonus Time");
				break;
			default:
				break;
		}
		
		switch (obType) {
			case "PlayerShip" :
				System.out.println("Player lost a life");
				e2.toDestroy();
				break;
			case "Ship" : 
				System.out.println("Ship destroy");
				e2.toDestroy();
				break;
			case "Shot" : 
				System.out.println("Shot Hit");
				Shot shot = (Shot) e2;
				shot.receive(AbstractShot.MESSAGE_HIT);
				break;
			case "Bonus" :
				System.out.println("Bonus Time");
				break;
			default:
				break;
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
		arg0.getFixtureA().getBody().setType(BodyType.STATIC);
		arg0.getFixtureB().getBody().setType(BodyType.STATIC);
	}

}
