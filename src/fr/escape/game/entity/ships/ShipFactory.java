package fr.escape.game.entity.ships;

import java.util.List;
import java.util.Objects;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import fr.escape.app.Foundation;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.weapons.Weapon;
import fr.escape.game.entity.weapons.Weapons;
import fr.escape.game.entity.weapons.shot.ShotFactory;
import fr.escape.graphics.AnimationTexture;
import fr.escape.resources.texture.TextureLoader;

public class ShipFactory {
	private static final int PLAYERMASK = 0x0004 | 0x0008 | 0x000F;
	private static final int NPCMASK = 0x0002 | 0x0008;
	
	private final World world;
	private final EntityContainer econtainer;
	private final List<Weapon> playerWeapons;
	private final List<Weapon> npcWeapons;
	
	public ShipFactory(World world, EntityContainer ec, ShotFactory factory) {
		this.world = world;
		this.econtainer = ec;
		this.playerWeapons = Weapons.createListOfWeapons(this.econtainer, factory);
		this.npcWeapons = Weapons.createListOfUnlimitedWeapons(this.econtainer, factory);
	}
	
	public RegularShip createRegularShip(float x, float y, boolean isPlayer) {
		
		Objects.requireNonNull(world);
		
		float shapeX = CoordinateConverter.toMeterX(Foundation.RESOURCES.getTexture(TextureLoader.SHIP_SWING).getWidth() / 2);
		float shapeY = CoordinateConverter.toMeterY(Foundation.RESOURCES.getTexture(TextureLoader.SHIP_SWING).getHeight() / 2);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.DYNAMIC;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(shapeX, shapeY);
		
		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.density = 0.5f;
		fixture.friction = 0.0f;      
		fixture.restitution = 0.0f;
		
		if(isPlayer) {
			fixture.filter.categoryBits = 0x0002;
			fixture.filter.maskBits = PLAYERMASK;
		} else {
			fixture.filter.categoryBits = 0x0004;
			fixture.filter.maskBits = NPCMASK;
		}
		
		Body body = world.createBody(bodyDef);
		body.createFixture(fixture);
		
		List<Weapon> weapons;
		
		if(isPlayer) {
			weapons = playerWeapons;
		} else {
			weapons = npcWeapons;
		}
		
		RegularShip ship = new RegularShip(body, weapons, isPlayer, 1, econtainer, new AnimationTexture( 
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_SWING),
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_SWING_1),
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_SWING_2),
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_SWING_3),
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_SWING_4),
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_SWING_5),
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_SWING_6),
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_SWING_7),
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_SWING_8),
				Foundation.RESOURCES.getTexture(TextureLoader.SHIP_SWING_9)
		));
		
		if(!isPlayer) {
			ship.setRotation(180);
		}

		body.setUserData(ship);
		
		return ship;
	}
	
	// TODO
	public Ship createShipForScenario(int type, float x, float y) {
		
		switch(type) {
			case 0: {
				Ship ship = createRegularShip(x, y, false);
				ship.getBody().setActive(false);
				return ship;
			}
			case 1: {
				break;
			}
			case 2: {
				break;
			}
			default: {
				throw new IllegalArgumentException("Unknown Ship Type");
			}
		}
		
		return null;
	}
	
}
