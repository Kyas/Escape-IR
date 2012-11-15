package fr.escape.game.entity.ships;

import java.util.List;
import java.util.Objects;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.CollisionBehavior;
import fr.escape.game.entity.Collisionable;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.weapons.Weapon;
import fr.escape.game.entity.weapons.Weapons;
import fr.escape.game.entity.weapons.shot.Shot;
import fr.escape.game.entity.weapons.shot.ShotFactory;
import fr.escape.graphics.AnimationTexture;
import fr.escape.resources.texture.TextureLoader;

//TODO Comment
public class ShipFactory {
	
	private static final int PLAYERMASK = Collisionable.NPC_TYPE | Collisionable.SHOT_TYPE | Collisionable.BONUS_TYPE | Collisionable.WALL_TYPE;
	private static final int NPCMASK = Collisionable.PLAYER_TYPE | Collisionable.SHOT_TYPE;
	
	private static final CollisionBehavior COMPUTER_COLLISION_BEHAVIOR = new ComputerShipCollisionBehavior();
	private static final CollisionBehavior PLAYER_COLLISION_BEHAVIOR = new PlayerShipCollisionBehavior();
	
	private static final int DEFAULT_ARMOR = 1;
	private static final int PLAYER_ARMOR = 10;
	
	private final EntityContainer econtainer;
	private final List<Weapon> playerWeapons;
	private final List<Weapon> npcWeapons;
	
	public ShipFactory(EntityContainer ec, ShotFactory factory) {
		this.econtainer = Objects.requireNonNull(ec);
		this.playerWeapons = Weapons.createListOfWeapons(this.econtainer, Objects.requireNonNull(factory));
		this.npcWeapons = Weapons.createListOfUnlimitedWeapons(this.econtainer, factory);
	}
	
	public Ship createFalcon(float x, float y) {
		
		AnimationTexture falcon = new AnimationTexture(Foundation.RESOURCES.getTexture(TextureLoader.SHIP_FALCON));
		
		BodyDef bodyDef = createBodyDef(x, y);
		FixtureDef fixture = createFixtureForNpc(falcon);
		
		return createNpcAbstractShip(bodyDef, fixture, falcon);
		
	}
	
	public Ship createViper(float x, float y) {
		
		AnimationTexture vyper = new AnimationTexture(Foundation.RESOURCES.getTexture(TextureLoader.SHIP_VIPER));
		
		BodyDef bodyDef = createBodyDef(x, y);
		FixtureDef fixture = createFixtureForNpc(vyper);
		
		return createNpcAbstractShip(bodyDef, fixture, vyper);
		
	}
	
	public Ship createRaptor(float x, float y) {
		
		AnimationTexture raptor = new AnimationTexture(Foundation.RESOURCES.getTexture(TextureLoader.SHIP_RAPTOR));
		
		BodyDef bodyDef = createBodyDef(x, y);
		FixtureDef fixture = createFixtureForNpc(raptor);
		
		Ship ship = createNpcAbstractShip(bodyDef, fixture, raptor);
		ship.setRotation(180);
		
		return ship;
	}
	
	public Ship createPlayer(float x, float y) {
		
		AnimationTexture raptor = new AnimationTexture( 
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_RAPTOR),
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_RAPTOR_1),
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_RAPTOR_2),
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_RAPTOR_3),
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_RAPTOR_4),
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_RAPTOR_5),
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_RAPTOR_6),
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_RAPTOR_7),
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_RAPTOR_8),
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_RAPTOR_9)
		);
		
		BodyDef bodyDef = createBodyDef(x, y);
		FixtureDef fixture = createFixtureForPlayer(raptor);

		return new AbstractShip(bodyDef, fixture, playerWeapons, PLAYER_ARMOR, econtainer, raptor, PLAYER_COLLISION_BEHAVIOR) {
			
			private static final int PLAYER_MASK = NPC_TYPE | SHOT_TYPE | BONUS_TYPE | WALL_TYPE;
			private static final int INVULNERABILITY_MASK = 0x0001 | BONUS_TYPE;
			private static final int LEFTLOOP = 2;
			private static final int RIGHTLOOP = 1;
			
			private boolean executeLeftLoop = false;
			private boolean executeRightLoop = false;
			
			@Override
			public boolean isPlayer() {
				return true;
			}
			
			@Override
			public void toDestroy() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public void update(Graphics graphics, long delta) {
				
				if(executeRightLoop || executeLeftLoop) {
					
					if(executeRightLoop) {
						getShipDrawable().forward();
					} else {
						getShipDrawable().reverse();
					}
					
					if(getShipDrawable().hasNext()) {
						getShipDrawable().next();
					} else {
						getShipDrawable().rewind();
						executeLeftLoop = false;
						executeRightLoop = false;
					}
				}
				
				super.update(graphics, delta);
			}
			
			@Override
			public void moveBy(float[] velocity) {
				
				if(getBody().isActive()) {
					
					doLooping(velocity);
					
					if(velocity[0] > 0) {
						
						getBody().setLinearVelocity(new Vec2(velocity[1], velocity[2]));
						velocity[0] -= Math.abs(Math.max(Math.abs(velocity[1]), Math.abs(velocity[2])));
						
					} else {
						getBody().setLinearVelocity(new Vec2(0, 0));
						velocity[1] = 0.0f;
						velocity[2] = 0.0f;
					}
					
					Shot shot = getActiveWeapon().getShot();
					
					if(shot != null) {
						shot.setPosition(getX(), getY() - CoordinateConverter.toMeterY((int) getEdge().getHeight()));
					}

				}
			}
			
			/**
			 * Make Player Invulnerable, or not.
			 * 
			 * @param invulnerable Is Player Invulnerable ?
			 */
			private void setInvulnerable(boolean invulnerable) {
				getBody().getFixtureList().m_filter.maskBits = (invulnerable)?INVULNERABILITY_MASK:PLAYER_MASK;
			}
			
			private void doLooping(float[] velocity) {
				
				int mode = (int) velocity[3];
				switch (mode) {
					case RIGHTLOOP: {
						setInvulnerable(true);
						executeRightLoop = true;
						if(velocity[0] <= 0) {
							velocity[3] = 0.0f;
						} else {
							velocity[0] -= 2.0f;
						}
						break;
					}
					case LEFTLOOP: {
						setInvulnerable(true);
						executeLeftLoop = true;
						if(velocity[0] <= 0) {
							velocity[3] = 0.0f;
						} else {
							velocity[0] -= 2.0f;
						}
						break;
					}
					default: {
						getShipDrawable().rewind();
						executeLeftLoop = false;
						executeRightLoop = false;
						setInvulnerable(false);
						break;
					}
				}

			}
		
		};
		
	}
	
	// TODO
	public Ship createShipForScenario(int type, float x, float y) {
		switch(type) {
			case 0: {
				return createRaptor(x, y);
			}
			case 1: {
				return createFalcon(x, y);
			}
			case 2: {
				return createViper(x, y);
			}
			default: {
				throw new IllegalArgumentException("Unknown Ship Type");
			}
		}
	}
	
	public Boss createJupiterBoss(float x, float y) {
		
		AnimationTexture jupiter = new AnimationTexture(Foundation.RESOURCES.getTexture(TextureLoader.BOSS_JUPITER));
		
		BodyDef bodyDef = createBodyDef(x, y);
		FixtureDef fixture = createFixtureForNpc(jupiter);
		
		return new AbstractBoss(bodyDef, fixture, npcWeapons, DEFAULT_ARMOR, econtainer, jupiter, COMPUTER_COLLISION_BEHAVIOR) {

			@Override
			public int getFireWaitingTime() {
				return 3000;
			}

			@Override
			public int getSpecialWaitingTime() {
				return 10000;
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
				
				incActionCount();
			}
		};
		
	}
	
	public Boss createMoonBoss(float x, float y) {
		
		AnimationTexture moon = new AnimationTexture(Foundation.RESOURCES.getTexture(TextureLoader.BOSS_MOON));
		
		BodyDef bodyDef = createBodyDef(x, y);
		FixtureDef fixture = createFixtureForNpc(moon);
		
		return new AbstractBoss(bodyDef, fixture, npcWeapons, DEFAULT_ARMOR, econtainer, moon, COMPUTER_COLLISION_BEHAVIOR) {

			@Override
			public int getFireWaitingTime() {
				return 3000;
			}

			@Override
			public int getSpecialWaitingTime() {
				return 10000;
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
				
				incActionCount();
			}
		};
		
	}

	public Boss createEarthBoss(float x, float y) {
		
		AnimationTexture earth = new AnimationTexture(Foundation.RESOURCES.getTexture(TextureLoader.BOSS_EARTH));
		
		BodyDef bodyDef = createBodyDef(x, y);
		FixtureDef fixture = createFixtureForNpc(earth);
		
		return new AbstractBoss(bodyDef, fixture, npcWeapons, DEFAULT_ARMOR, econtainer, earth, COMPUTER_COLLISION_BEHAVIOR) {
	
			@Override
			public int getFireWaitingTime() {
				return 3000;
			}
	
			@Override
			public int getSpecialWaitingTime() {
				return 10000;
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
				
				incActionCount();
			}
		};
		
	}
	
	private static BodyDef createBodyDef(float x, float y) {
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.DYNAMIC;
		
		return bodyDef;
	}
	
	private static PolygonShape createShape(AnimationTexture drawable) {
		
		Objects.requireNonNull(drawable);
		
		float shapeX = CoordinateConverter.toMeterX(drawable.getWidth() / 2);
		float shapeY = CoordinateConverter.toMeterY(drawable.getHeight() / 2);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(shapeX, shapeY);
		
		return shape;
	}
	
	private static FixtureDef createFixtureForNpc(AnimationTexture drawable) {
		
		Objects.requireNonNull(drawable);
		
		FixtureDef fixture = new FixtureDef();
		fixture.shape = createShape(drawable);
		fixture.density = 0.5f;
		fixture.friction = 0.3f;      
		fixture.restitution = 0.0f;
		fixture.filter.categoryBits = 0x0004;
		fixture.filter.maskBits = NPCMASK;
		
		return fixture;
	}
	
	private static FixtureDef createFixtureForPlayer(AnimationTexture drawable) {
		
		FixtureDef fixture = createFixtureForNpc(drawable);
		
		fixture.filter.categoryBits = 0x0002;
		fixture.filter.maskBits = PLAYERMASK;
		
		return fixture;
	}
	
	private Ship createNpcAbstractShip(BodyDef bodyDef, FixtureDef fixture, AnimationTexture drawable) {
		
		return new AbstractShip(bodyDef, fixture, npcWeapons, DEFAULT_ARMOR, econtainer, drawable, COMPUTER_COLLISION_BEHAVIOR) {
			
			@Override
			public void toDestroy() {
				Foundation.ACTIVITY.post(new Runnable() {
					
					@Override
					public void run() {
						popBonus();
					}
					
				});
			}
			
			void popBonus() {
				getEntityContainer().pushBonus(getX(), getY());
				getEntityContainer().destroy(this);
			}

			@Override
			public boolean isPlayer() {
				return false;
			}
			
		};
	}
}
