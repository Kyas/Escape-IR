package fr.escape.game.entity.ships;

import java.util.List;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.CollisionBehavior;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.weapons.Weapon;
import fr.escape.game.entity.weapons.shot.EarthShot;
import fr.escape.game.entity.weapons.shot.JupiterShot;
import fr.escape.game.entity.weapons.shot.MoonShot;
import fr.escape.game.entity.weapons.shot.Shot;
import fr.escape.game.entity.weapons.shot.Shot.ShotContext;
import fr.escape.game.entity.weapons.shot.ShotFactory;
import fr.escape.graphics.AnimationTexture;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public abstract class AbstractBoss extends AbstractShip implements Boss {
	
	private boolean moveToRight;
	private long timer;
	private int actionCount;
	private boolean specialMode;
	
	//TODO remove after redefinition de special dans la factory
	final EntityContainer container;
	
	public AbstractBoss(BodyDef bodyDef, FixtureDef fixture, List<Weapon> weapons, 
			int life, EntityContainer container, AnimationTexture textures,
			CollisionBehavior collisionBehavior) {
		
		super(bodyDef, fixture, weapons, life, container, textures, collisionBehavior);
		
		moveToRight = false;
		timer = 0;
		actionCount = 1;
		specialMode = false;
		
		this.container = container;
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
			specialMode = true;
		}
		
		// Do we need to trigger Fire Action / Switch to Normal ?
		if((timer / actionCount) >= getFireWaitingTime()) {
			if(specialMode) {
				specialMode = false;
				normal();
			} else {
				fire();
			}	
		}
		
	}

	@Override
	public void toDestroy() {
		Foundation.ACTIVITY.post(new Runnable() {

			@Override
			public void run() {
				getEntityContainer().destroy(AbstractBoss.this);
			}
			
		});
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
		ShotFactory shotFactory = new ShotFactory(Foundation.ACTIVITY.getWorld(), container);

		//Jupiter Test
		/*Texture texture = Foundation.RESOURCES.getTexture(TextureLoader.JUPITER_SPECIAL);
		
		final JupiterShot s1 = (JupiterShot) shotFactory.createJupiterShot(getX(), getY());
		final JupiterShot s2 = (JupiterShot) shotFactory.createJupiterShot(getX(), getY());
		final JupiterShot s3 = (JupiterShot) shotFactory.createJupiterShot(getX(), getY());
		final JupiterShot s4 = (JupiterShot) shotFactory.createJupiterShot(getX(), getY());
		
		s1.setShotConfiguration(new ShotContext(isPlayer(), texture.getWidth(), texture.getHeight()));
		s2.setShotConfiguration(new ShotContext(isPlayer(), texture.getWidth(), texture.getHeight()));
		s3.setShotConfiguration(new ShotContext(isPlayer(), texture.getWidth(), texture.getHeight()));
		s4.setShotConfiguration(new ShotContext(isPlayer(), texture.getWidth(), texture.getHeight()));
		
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
		});*/
		
		/*Texture texture = Foundation.RESOURCES.getTexture(TextureLoader.MOON_SPECIAL);
		final MoonShot s1 = (MoonShot) shotFactory.createMoonShot(getX() - CoordinateConverter.toMeterX(20), getY() - CoordinateConverter.toMeterY(9));
		
		s1.setShotConfiguration(new ShotContext(isPlayer(), texture.getWidth(), texture.getHeight()));
		s1.moveBy(new float[] {0.0f, 0.0f, 2.5f});
		s1.receive(Shot.MESSAGE_CRUISE);
		
		Foundation.ACTIVITY.post(new Runnable() {
			@Override
			public void run() {
				container.push(s1);
			}
		});*/
		
		Texture texture = Foundation.RESOURCES.getTexture(TextureLoader.EARTH_SPECIAL);
		final EarthShot s1 = (EarthShot) shotFactory.createEarthShot(getX(), getY());
		
		s1.setShotConfiguration(new ShotContext(isPlayer(), texture.getWidth(), texture.getHeight()));
		s1.moveBy(new float[] {0.0f, 0.0f, 0.0f});
		s1.receive(Shot.MESSAGE_CRUISE);
		
		Foundation.ACTIVITY.post(new Runnable() {
			@Override
			public void run() {
				container.push(s1);
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
			moveTo(1.0f, 2.0f);
			
		} else if(x <= 1.0f) {
			
			moveToRight = false;
			moveTo(9.0f, 2.0f);
			
		}
		
	}
	
}
