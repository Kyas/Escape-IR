package fr.escape.game.entity.weapons.shot;

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
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

// TODO Comment
public final class ShotFactory {
	
	private static final int MASK = 0x0001;
	private static final ShotCollisionBehavior COLLISION_BEHAVIOR = new ShotCollisionBehavior();
	
	private final World world;
	private final EntityContainer entityContainer;

	public ShotFactory(World world, EntityContainer entityContainer) {
		this.world = Objects.requireNonNull(world);
		this.entityContainer = Objects.requireNonNull(entityContainer);
	}

	public Shot createBlackholeShot(float x, float y) {

		// TODO Find something more elegant
		float shapeX = CoordinateConverter.toMeterX(Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_BLACKHOLE_CORE_SHOT).getWidth() / 2);
		float shapeY = CoordinateConverter.toMeterY(Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_BLACKHOLE_CORE_SHOT).getHeight() / 2);
		
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
		fixture.filter.categoryBits = 0x0008;
		fixture.filter.maskBits = MASK;
		
		Body body = world.createBody(bodyDef);
		body.createFixture(fixture);
		
		Shot shot = new BlackHoleShot(body, entityContainer, COLLISION_BEHAVIOR);
		
		body.setUserData(shot);
		
		return shot;
	}
	
	public Shot createFireBallShot(float x, float y) {

		// TODO Find something more elegant
		float shapeX = CoordinateConverter.toMeterX(Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_FIREBALL_CORE_SHOT).getWidth() / 2);
		float shapeY = CoordinateConverter.toMeterY(Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_FIREBALL_CORE_SHOT).getHeight() / 2);

		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.DYNAMIC;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(shapeX, shapeY);
		
		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.density = 0.5f;
		fixture.friction = 1.0f;
		fixture.restitution = 0.0f;
		fixture.filter.categoryBits = 0x0008;
		fixture.filter.maskBits = MASK;
		
		Body body = world.createBody(bodyDef);
		body.createFixture(fixture);

		Shot shot = new FireBallShot(body, entityContainer, COLLISION_BEHAVIOR);
		
		body.setUserData(shot);
		
		return shot;
	}
	
	public Shot createMissileShot(float x, float y) {

		// TODO Find something more elegant
		float shapeX = CoordinateConverter.toMeterX(Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_FIREBALL_CORE_SHOT).getWidth() / 2);
		float shapeY = CoordinateConverter.toMeterY(Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_FIREBALL_CORE_SHOT).getHeight() / 2);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.DYNAMIC;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(shapeX, shapeY);
		
		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.density = 0.5f;
		fixture.friction = 1.0f;       
		fixture.restitution = 0.0f;
		fixture.filter.categoryBits = 0x0008;
		fixture.filter.maskBits = MASK;
		
		Body body = world.createBody(bodyDef);
		body.createFixture(fixture);

		Shot shot = new MissileShot(body, entityContainer, COLLISION_BEHAVIOR);
		
		body.setUserData(shot);
		
		return shot;
	}

	public Shot createShiboleetShot(float x, float y, boolean isChild) {
		Texture coreShiboleet = Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_SHIBOLEET_SHOT);
		float shapeX, shapeY;

		// TODO Find something more elegant
		float shipSize = CoordinateConverter.toMeterY(Foundation.RESOURCES.getTexture(TextureLoader.SHIP_RAPTOR).getHeight());
		if(isChild) {
			shapeX = CoordinateConverter.toMeterX(coreShiboleet.getWidth() / 2 - 10);
			shapeY = CoordinateConverter.toMeterY(coreShiboleet.getHeight() / 2 - 10);
		} else {
			shapeX = CoordinateConverter.toMeterX((int) ((coreShiboleet.getWidth() / 2) * ShiboleetShot.CHILD_RADIUS));
			shapeY = CoordinateConverter.toMeterY((int) ((coreShiboleet.getHeight() / 2) * ShiboleetShot.CHILD_RADIUS));
		}
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y + ((isChild)?0:shipSize));
		bodyDef.type = BodyType.DYNAMIC;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(shapeX, shapeY);
		
		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.density = 0.5f;
		fixture.friction = 1.0f;       
		fixture.restitution = 0.0f;
		fixture.filter.categoryBits = 0x0008;
		fixture.filter.maskBits = MASK;
		
		Body body = world.createBody(bodyDef);
		body.createFixture(fixture);

		Shot shot = new ShiboleetShot(body, isChild, entityContainer, COLLISION_BEHAVIOR, this);
		
		body.setUserData(shot);
		
		return shot;
	}
	
}
