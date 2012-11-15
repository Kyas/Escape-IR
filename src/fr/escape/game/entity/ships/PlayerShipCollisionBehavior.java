package fr.escape.game.entity.ships;

import java.util.Objects;

import fr.escape.app.Foundation;
import fr.escape.game.User;
import fr.escape.game.entity.CollisionBehavior;
import fr.escape.game.entity.Collisionable;
import fr.escape.game.entity.Entity;
import fr.escape.game.entity.bonus.Bonus;
import fr.escape.game.entity.weapons.shot.Shot;

/**
 * {@link CollisionBehavior} for Player Ship.
 * 
 */
public final class PlayerShipCollisionBehavior implements CollisionBehavior {

	private static final String TAG = PlayerShipCollisionBehavior.class.getSimpleName();
	
	@Override
	public void applyCollision(User user, Entity handler, Entity other, int type) {

		Objects.requireNonNull(user);
		Objects.requireNonNull(other);
		
		switch(type) {
			case Collisionable.SHOT_TYPE: { 
				
				Foundation.ACTIVITY.debug(TAG, "Player hit a Shot.");
				
				Shot shot = (Shot) other;
				shot.receive(Shot.MESSAGE_HIT);

				user.getShip().damage(user,shot.getDamage());
				
				break;
			}
			case Collisionable.BONUS_TYPE: {
				
				Foundation.ACTIVITY.debug(TAG, "Player hit a Bonus.");
					
				Bonus bonus = (Bonus) other;
				user.addBonus(bonus.getWeapon(), bonus.getNumber());
					
				bonus.toDestroy();
				
				break;
			}
			case Collisionable.NPC_TYPE: {
				
				Foundation.ACTIVITY.debug(TAG, "Player hit a NPC.");
				
				Ship ship = (Ship) other;
				user.getShip().damage(user,1);
				user.addScore(50);
				
				ship.damage(user,1);
				
				break;
			}
			default: { 
				throw new IllegalStateException("Unknown touch contact {"+handler+", "+other+"}");
			}
		}
	}

}
