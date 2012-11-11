package fr.escape.game.entity;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;

import fr.escape.app.Foundation;
import fr.escape.game.entity.weapons.shot.AbstractShot;
import fr.escape.game.entity.weapons.shot.Shot;

// TODO Comment
public final class CollisionDetector implements ContactListener {

	public static final int BONUS_TYPE = 0x000F;
	public static final int PLAYER_TYPE = 0x0002;
	public static final int SHOT_TYPE = 0x0008;
	
	private static final String TAG = CollisionDetector.class.getSimpleName();
	
	@Override
	public void beginContact(Contact arg0) {
		
		Entity entityA = (Entity) arg0.getFixtureA().getBody().getUserData();
		Entity entityB = (Entity) arg0.getFixtureB().getBody().getUserData();
		
		int typeA = arg0.getFixtureA().getFilterData().categoryBits;
		int typeB = arg0.getFixtureB().getFilterData().categoryBits;
		
		// TODO Remove it and use an Interface instead.
		switch (typeA) {
			/**
			 * Player Ship
			 */
			case PLAYER_TYPE: {
				switch(typeB) {
					case SHOT_TYPE: {
						Shot shot = (Shot) entityB;
						shot.receive(AbstractShot.MESSAGE_HIT);
						System.out.println("Hit by shot, you lost a life");
						/*entityA.toDestroy();*/
						break;
					}
					case BONUS_TYPE: {
						/*Bonus bonus = (Bonus) entityB;
						Ship ship = (Ship) entityA;*/
						entityB.toDestroy();
						System.out.println("Add ammunitions + desactivate bonus");
						break;
					}
					default: {
						/*entityA.toDestroy();*/
						entityB.toDestroy();
						error("Hit by unknown contact, player lost a life anyway");
						break;
					}
				}
				break;
			}
			/**
			 * NPC Ship
			 */
			case 0x0004 :
				if(typeB == 0x0008) {
					Shot shot = (Shot) entityB;
					shot.receive(AbstractShot.MESSAGE_HIT);
				}
				System.out.println("Destroy both entity");
				entityA.toDestroy();
				if(typeB == 0x0002) {
					System.out.println("Player lost a life");
				} else {
					entityB.toDestroy();
				}
				break;
			/**
			 * Shot
			 */
			case 0x0008 :
				Shot shot = (Shot) entityA;
				shot.receive(AbstractShot.MESSAGE_HIT);
				System.out.println("Shot hit & destroy the other entity");
				if(typeB == 0x0002) {
					System.out.println("Player lost a life");
				} else {
					entityB.toDestroy();
				}
				break;
			/**
			 * Bonus
			 */
			case BONUS_TYPE: {
				switch(typeB) {
					case PLAYER_TYPE: {
						debug("Touch player - desactivate + add ammo");
						entityA.toDestroy();
						break;
					}
					default: {
						error("Unknown touch contact {"+entityA+", "+entityB+"}");
						entityA.toDestroy();
						break;
					}
				}
			}
		}
		
	}

	private void debug(String message) {
		Foundation.ACTIVITY.debug(TAG, message);
	}
	
	private void error(String message) {
		Foundation.ACTIVITY.error(TAG, message);
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
