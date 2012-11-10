package fr.escape.game.entity.weapons.shot;

import java.util.ArrayList;
import java.util.Objects;

import org.jbox2d.collision.shapes.CircleShape;
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
	
	//private static final Texture MISSILE_SHOT_TEXTURE = Foundation.resources.getTexture(TextureLoader.WEAPON_MISSILE_SHOT);
	//private static final Texture SHIBOLEET_SHOT_TEXTURE = Foundation.resources.getTexture(TextureLoader.WEAPON_SHIBOLEET_SHOT);

	public static Shot createBlackholeShot(World world, float x, float y, EntityContainer ec) {
		Objects.requireNonNull(world);
		float shapeX = CoordinateConverter.toMeterX(Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_BLACKHOLE_CORE_SHOT).getWidth() / 2);
		float shapeY = CoordinateConverter.toMeterY(Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_BLACKHOLE_CORE_SHOT).getHeight() / 2);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.DYNAMIC;
		
		CircleShape shape = new CircleShape();
		shape.m_p.set(shapeX,shapeY);
		shape.m_radius = shapeX;
				
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
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.userData = "FireBall";
		
		CircleShape shape = new CircleShape();
		shape.m_radius = 0.0f;
		
		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.density = 0.5f;
		fixture.friction = 1.0f;       
		fixture.restitution = 0.0f;
		
		Body body = world.createBody(bodyDef);
		body.createFixture(fixture);

		Shot shot = new BlackHoleShot(body, ec, ec);
		
		ArrayList<Object> userData = new ArrayList<>(2);
		userData.add(0,"Shot");
		userData.add(1,shot);
		body.setUserData(userData);
		
		return shot;
	}
	
//	public static Shot createMissileShot(EntityContainer ec) {
//		return new AbstractShot(MISSILE_SHOT_TEXTURE, ec) {
//
//			@Override
//			public void receive(int message) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void update(Graphics graphics, long delta) {
//				
//				draw(graphics);
//				
//				Foundation.activity.post(new Runnable() {
//					
//					@Override
//					public void run() {
//						moveBy(0, 3);
//						rotateBy(1);
//					}
//
//				});
//			}
//			
//		};
//	}
//	
//	public static Shot createShiboleetShot(EntityContainer ec) {
//		
//		return new AbstractShot(SHIBOLEET_SHOT_TEXTURE, ec) {
//
//			@Override
//			public void receive(int message) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void update(Graphics graphics, long delta) {
//				
//				draw(graphics);
//				
//				Foundation.activity.post(new Runnable() {
//					
//					@Override
//					public void run() {
//						moveBy(0, 3);
//						rotateBy(1);
//					}
//
//				});
//			}
//			
//		};
//	}
}
