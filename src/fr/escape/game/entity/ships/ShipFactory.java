package fr.escape.game.entity.ships;

import java.util.List;
import java.util.Objects;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.escape.app.Foundation;
import fr.escape.game.entity.CollisionBehavior;
import fr.escape.game.entity.Collisionable;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.weapons.Weapon;
import fr.escape.game.entity.weapons.Weapons;
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
		
		return new AbstractShip(bodyDef, fixture, npcWeapons, false, DEFAULT_ARMOR, econtainer, falcon, COMPUTER_COLLISION_BEHAVIOR) {
			
		};
		
	}
	
	public Ship createViper(float x, float y) {
		
		AnimationTexture vyper = new AnimationTexture(Foundation.RESOURCES.getTexture(TextureLoader.SHIP_VIPER));
		
		BodyDef bodyDef = createBodyDef(x, y);
		FixtureDef fixture = createFixtureForNpc(vyper);
		
		return new AbstractShip(bodyDef, fixture, npcWeapons, false, DEFAULT_ARMOR, econtainer, vyper, COMPUTER_COLLISION_BEHAVIOR) {
			
		};
		
	}
	
	public Ship createRaptor(float x, float y) {
		
		AnimationTexture raptor = new AnimationTexture(Foundation.RESOURCES.getTexture(TextureLoader.SHIP_RAPTOR));
		
		BodyDef bodyDef = createBodyDef(x, y);
		FixtureDef fixture = createFixtureForNpc(raptor);
		
		Ship ship = new AbstractShip(bodyDef, fixture, npcWeapons, false, DEFAULT_ARMOR, econtainer, raptor, COMPUTER_COLLISION_BEHAVIOR) {
			
		};
		
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

		return new AbstractShip(bodyDef, fixture, playerWeapons, true, PLAYER_ARMOR, econtainer, raptor, PLAYER_COLLISION_BEHAVIOR) {
			
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
}
