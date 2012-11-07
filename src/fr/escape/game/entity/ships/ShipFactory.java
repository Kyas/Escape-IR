package fr.escape.game.entity.ships;

import java.util.Objects;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class ShipFactory {
	
	public RegularShip createRegularShip(World world, float x, float y, BodyType type, float radius, boolean isPlayer) {
		
		Objects.requireNonNull(world);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x,y);
		bodyDef.type = type;
		
		CircleShape shape = new CircleShape();
		shape.m_radius = radius;
		
		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.density = 0.5f;
		fixture.friction = 0.3f;       
		fixture.restitution = 0.5f;
		
		Body body = world.createBody(bodyDef);
		body.createFixture(fixture);
		RegularShip ship = new RegularShip(body,isPlayer);
		body.setUserData((isPlayer)?"PlayerShip":"NPCShip");
		
		return ship;
	}
	
}
