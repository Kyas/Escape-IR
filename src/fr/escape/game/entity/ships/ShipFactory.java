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

import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.weapons.Weapon;

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
		fixture.friction = 1.0f;       
		fixture.restitution = 0.0f;
		
		Body body = world.createBody(bodyDef);
		body.createFixture(fixture);
		RegularShip ship = new RegularShip(body,weapons,isPlayer,ec,ec);
		
		ArrayList<Object> userData = new ArrayList<>(2);
		userData.add(0,(isPlayer)?"PlayerShip":"NPCShip");
		userData.add(1,ship);
		body.setUserData(userData);
		
		return ship;
	}
	
}
