package fr.escape.game.entity.ships;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import fr.escape.app.Foundation;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.weapons.Weapon;
import fr.escape.graphics.AnimationTexture;
import fr.escape.resources.texture.TextureLoader;

public class ShipFactory {
	
	public RegularShip createRegularShip(World world, float x, float y, BodyType type, float radius, boolean isPlayer, EntityContainer ec, List<Weapon> weapons) {
		
		Objects.requireNonNull(world);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = type;
		
		CircleShape shape = new CircleShape();
		shape.m_radius = radius;
		
		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.density = 0.5f;
		fixture.friction = 0.0f;      
		fixture.restitution = 0.0f;
		
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
		userData.add(0,(isPlayer)?"PlayerShip":"Ship");
		userData.add(1,ship);
		body.setUserData(userData);
		
		return ship;
	}
	
}
