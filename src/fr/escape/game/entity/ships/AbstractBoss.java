package fr.escape.game.entity.ships;

import java.util.List;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import fr.escape.app.Graphics;
import fr.escape.game.entity.CollisionBehavior;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.weapons.Weapon;
import fr.escape.graphics.AnimationTexture;

public abstract class AbstractBoss extends AbstractShip implements Boss {
	
	private boolean moveToRight;
	private long timer;
	private int actionCount;
	
	public AbstractBoss(BodyDef bodyDef, FixtureDef fixture, List<Weapon> weapons, 
			int life, EntityContainer container, AnimationTexture textures,
			CollisionBehavior collisionBehavior) {
		
		super(bodyDef, fixture, weapons, life, container, textures, collisionBehavior);
		
		moveToRight = false;
		timer = 0;
		actionCount = 1;
		
	}
	
	@Override
	public void update(Graphics graphics, long delta) {

		timer += delta;
		
		// Handle Move
		move();
		
		draw(graphics);
		
		// Do we need to trigger Special Action ?
		if(timer >= getSpecialWaitingTime()) {
			special();
		}
		
		// Do we need to trigger Fire Action ?
		if((timer / actionCount) >= getFireWaitingTime()) {
			fire();	
		}
		
	}

	@Override
	public void toDestroy() {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public boolean isPlayer() {
		return false;
	}
	
	/**
	 * Get Timer Value
	 *  
	 * @return Timer Value
	 */
	protected long getTimer() {
		return timer;
	}
	
	/**
	 * Reset the Timer
	 */
	protected void resetTimer() {
		timer = 0;
	}

	/**
	 * Get Action Count Value
	 * 
	 * @return Action Count
	 */
	protected int getActionCount() {
		return actionCount;
	}
	
	/**
	 * Reset Action Count
	 */
	protected void resetActionCount() {
		actionCount = 1;
	}
	
	/**
	 * Increment Action Count by one
	 */
	protected void incActionCount() {
		actionCount++;
	}
	
	/**
	 * Get Waiting time on each fire
	 * 
	 * @return Waiting time
	 */
	public abstract int getFireWaitingTime();
	
	/**
	 * Get Waiting time on each special
	 * 
	 * @return Waiting time
	 */
	public abstract int getSpecialWaitingTime();
	
	@Override
	public void special() {
		
		//TODO fire special
		
		System.err.println("BOSS SPECIAL FIRE");
		
		resetTimer();
		resetActionCount();
	}

	@Override
	public void move() {
		
		float x = Math.abs(getBody().getLinearVelocity().x);
		
		if(!moveToRight && x <= 1.0f) {
			
			moveToRight = true;
			moveTo(1.0f, 2.0f);
			
		} else if(x <= 1.0f) {
			
			moveToRight = false;
			moveTo(9.0f, 2.0f);
			
		}
		
	}
	
}
