package fr.escape.game.entity;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;

import fr.escape.game.entity.weapons.shot.AbstractShot;
import fr.escape.game.entity.weapons.shot.Shot;

// TODO Comment
public final class CollisionDetector implements ContactListener {

	@Override
	public void beginContact(Contact arg0) {
		Entity entityA = (Entity) arg0.getFixtureA().getBody().getUserData();
		Entity entityB = (Entity) arg0.getFixtureB().getBody().getUserData();
		
		int typeA = arg0.getFixtureA().getFilterData().categoryBits;
		int typeB = arg0.getFixtureB().getFilterData().categoryBits;
		
		switch (typeA) {
			//PlayerShip
			case 0x0002 :
				switch(typeB) {
					case 0x0008 : 
						Shot shot = (Shot) entityB;
						shot.receive(AbstractShot.MESSAGE_HIT);
						System.out.println("Hit by shot, you lost a life");
						/*entityA.toDestroy();*/
						break;
					case 0x000F : 
						/*Bonus bonus = (Bonus) entityB;
						Ship ship = (Ship) entityA;*/
						System.out.println("Add ammunitions + desactivate bonus");
						break;
					default : 
						/*entityA.toDestroy();
						entityB.toDestroy();*/
						System.out.println("Hit by something, you lost a life");
						break;
				}
				break;
			//NPCShip
			case 0x0004 :
				System.out.println("Destroy both entity");
				/*entityA.toDestroy();*/
				if(typeB == 0x0002) System.out.println("Player lost a life");
				/*entityB.toDestroy();*/
				break;
			//Shot
			case 0x0008 :
				System.out.println("Shot hit & destroy the other entity");
				if(typeB == 0x0002) System.out.println("Player lost a life");
				break;
			//Bonus
			case 0x000F :
				System.out.println("Touch player - desactivate + add ammo");
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
