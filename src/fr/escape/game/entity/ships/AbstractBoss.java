package fr.escape.game.entity.ships;

import java.util.List;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.CollisionBehavior;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.weapons.Weapon;
import fr.escape.game.entity.weapons.shot.JupiterShot;
import fr.escape.game.entity.weapons.shot.Shot;
import fr.escape.game.entity.weapons.shot.Shot.ShotConfiguration;
import fr.escape.game.entity.weapons.shot.ShotFactory;
import fr.escape.graphics.AnimationTexture;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public abstract class AbstractBoss extends AbstractShip implements Boss {

	private static final int SPECIAL_FREQUENCY = 7000;
	
	private boolean moveToRight;
	private long timer;
	private int actionCount;
	
	//TODO remove after redefinition de special dans la factory
	final EntityContainer container;
	
	public AbstractBoss(BodyDef bodyDef, FixtureDef fixture, List<Weapon> weapons, 
			int life, EntityContainer container, AnimationTexture textures,
			CollisionBehavior collisionBehavior) {
		
		super(bodyDef, fixture, weapons, life, container, textures, collisionBehavior);
		
		moveToRight = false;
		timer = 0;
		actionCount = 1;
		
		this.container = container;
	}
	
	@Override
	public void update(Graphics graphics, long delta) {

		timer += delta;
		
		// Handle Move
		move();
		
		draw(graphics);
		
		//TODO Decommenter apres test
		if(timer >= SPECIAL_FREQUENCY/*getSpecialWaitingTime()*/) {
			special();
		}
		
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
		System.err.println("SPECIAL FIRE DONE");
		//TODO fire special
		Texture texture = Foundation.RESOURCES.getTexture(TextureLoader.JUPITER_SPECIAL);
		ShotFactory shotFactory = new ShotFactory(Foundation.ACTIVITY.getWorld(), container);
		
		final JupiterShot s1 = (JupiterShot) shotFactory.createJupiterShot(getX(), getY());
		final JupiterShot s2 = (JupiterShot) shotFactory.createJupiterShot(getX(), getY());
		final JupiterShot s3 = (JupiterShot) shotFactory.createJupiterShot(getX(), getY());
		final JupiterShot s4 = (JupiterShot) shotFactory.createJupiterShot(getX(), getY());
		
		s1.setShotConfiguration(new ShotConfiguration(isPlayer(), texture.getWidth(), texture.getHeight()));
		s2.setShotConfiguration(new ShotConfiguration(isPlayer(), texture.getWidth(), texture.getHeight()));
		s3.setShotConfiguration(new ShotConfiguration(isPlayer(), texture.getWidth(), texture.getHeight()));
		s4.setShotConfiguration(new ShotConfiguration(isPlayer(), texture.getWidth(), texture.getHeight()));
		
		s1.moveBy(new float[] {0.0f, 4.0f, 5.0f});
		s2.moveBy(new float[] {0.0f, 1.25f, 5.0f});
		s3.moveBy(new float[] {0.0f, -1.25f, 5.0f});
		s4.moveBy(new float[] {0.0f, -4.0f, 5.0f});
		
		s1.receive(Shot.MESSAGE_CRUISE);
		s2.receive(Shot.MESSAGE_CRUISE);
		s3.receive(Shot.MESSAGE_CRUISE);
		s4.receive(Shot.MESSAGE_CRUISE);
		
		Foundation.ACTIVITY.post(new Runnable() {
			@Override
			public void run() {
				container.push(s1);
				container.push(s2);
				container.push(s3);
				container.push(s4);
			}
		});
		
		resetTimer();
		resetActionCount();
	}

	@Override
	public void move() {
		
		float x = Math.abs(getBody().getLinearVelocity().x);
		
		if(!moveToRight && x <= 1.0f) {
			
			moveToRight = true;
			moveTo(1.0f, getY());
			
		} else if(x <= 1.0f) {
			
			moveToRight = false;
			moveTo(9.0f, getY());
			
		}
	}

	@Override
	public void fire() {
		
		setActiveWeapon(2);
		
		Foundation.ACTIVITY.post(new Runnable() {
			@Override
			public void run() {
				loadWeapon();
				fireWeapon(new float[]{0.0f, 0.0f, 5.0f});
			}
		});
		
		System.err.println("BOSS FIRE");
		++actionCount;
	}

}
