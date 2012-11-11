package fr.escape.game.entity.weapons.shot;

import java.util.ArrayList;
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
import fr.escape.resources.texture.TextureLoader;

public final class ShotFactory {

	public static Shot createBlackholeShot(World world, float x, float y, EntityContainer ec) {
		Objects.requireNonNull(world);
		float shapeX = CoordinateConverter.toMeterX(Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_BLACKHOLE_CORE_SHOT).getWidth() / 2);
		float shapeY = CoordinateConverter.toMeterY(Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_BLACKHOLE_CORE_SHOT).getHeight() / 2);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.DYNAMIC;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(shapeX,shapeY);
				
		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.density = 0.5f;
		fixture.friction = 0.0f;       
		fixture.restitution = 0.0f;
		fixture.filter.categoryBits = 0x0008;
		
		Body body = world.createBody(bodyDef);
		body.createFixture(fixture);
		
		Shot shot = new BlackHoleShot(body, ec, ec);
		
		ArrayList<Object> userData = new ArrayList<>(2);
		userData.add(0,"Shot");
		userData.add(1,shot);
		body.setUserData(userData);
		
		return shot;
	}
	
	public static Shot createFireBallShot(World world, float x, float y, EntityContainer ec) {
		Objects.requireNonNull(world);
		float shapeX = CoordinateConverter.toMeterX(Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_FIREBALL_CORE_SHOT).getWidth() / 2);
		float shapeY = CoordinateConverter.toMeterY(Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_FIREBALL_CORE_SHOT).getHeight() / 2);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.DYNAMIC;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(shapeX,shapeY);
		
		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.density = 0.5f;
		fixture.friction = 1.0f;       
		fixture.restitution = 0.0f;
		
		Body body = world.createBody(bodyDef);
		body.createFixture(fixture);

		Shot shot = new FireBallShot(body, ec, ec);
		
		ArrayList<Object> userData = new ArrayList<>(2);
		userData.add(0,"Shot");
		userData.add(1,shot);
		body.setUserData(userData);
		
		return shot;
	}
	
	public static Shot createMissileShot(World world, float x, float y, EntityContainer ec) {
		Objects.requireNonNull(world);
		float shapeX = CoordinateConverter.toMeterX(Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_FIREBALL_CORE_SHOT).getWidth() / 2);
		float shapeY = CoordinateConverter.toMeterY(Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_FIREBALL_CORE_SHOT).getHeight() / 2);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.DYNAMIC;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(shapeX,shapeY);
		
		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.density = 0.5f;
		fixture.friction = 1.0f;       
		fixture.restitution = 0.0f;
		
		Body body = world.createBody(bodyDef);
		body.createFixture(fixture);

		Shot shot = new MissileShot(body, ec, ec);
		
		ArrayList<Object> userData = new ArrayList<>(2);
		userData.add(0,"Shot");
		userData.add(1,shot);
		body.setUserData(userData);
		
		return shot;
	}

	public static Shot createShiboleetShot(World world, float x, float y, EntityContainer ec) {
		Objects.requireNonNull(world);
		float shapeX = CoordinateConverter.toMeterX(Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_SHIBOLEET_SHOT).getWidth() / 2);
		float shapeY = CoordinateConverter.toMeterY(Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_SHIBOLEET_SHOT).getHeight() / 2);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.DYNAMIC;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(shapeX,shapeY);
		
		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.density = 0.5f;
		fixture.friction = 1.0f;       
		fixture.restitution = 0.0f;
		
		Body body = world.createBody(bodyDef);
		body.createFixture(fixture);
		body.setActive(false);

		Shot shot = new ShiboleetShot(body, ec, ec);
		
		ArrayList<Object> userData = new ArrayList<>(2);
		userData.add(0,"Shot");
		userData.add(1,shot);
		body.setUserData(userData);
		
		return shot;
	}
	
}
