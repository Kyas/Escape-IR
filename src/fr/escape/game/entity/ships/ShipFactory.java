package fr.escape.game.entity.ships;

import java.util.ArrayList;
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
import fr.escape.graphics.AnimationTexture;
import fr.escape.resources.texture.TextureLoader;

public class ShipFactory {
	private final World world;
	private final EntityContainer ec;
	private final List<Weapon> weapons;
	
	public ShipFactory(World world, EntityContainer ec, List<Weapon> weapons) {
		this.world = world;
		this.ec = ec;
		this.weapons = weapons;
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
		if(isPlayer) fixture.filter.categoryBits = 0x0002;
		else fixture.filter.categoryBits = 0x0004;
		
		Body body = world.createBody(bodyDef);
		body.createFixture(fixture);
		RegularShip ship = new RegularShip(body, weapons, isPlayer, ec, ec, new AnimationTexture( 
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
		
		ArrayList<Object> userData = new ArrayList<>(2);
		userData.add(0,"Ship");
		userData.add(1,ship);
		body.setUserData(userData);
		
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
