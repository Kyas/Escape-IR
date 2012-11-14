package fr.escape.game.entity.bonus;

import java.util.Objects;
import java.util.Random;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import fr.escape.app.Foundation;
import fr.escape.game.entity.Collisionable;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.weapons.Weapons;
import fr.escape.resources.texture.TextureLoader;

//TODO Comment
public final class BonusFactory {
	
	private static final int MASK = Collisionable.PLAYER_TYPE;

	private static final int BLACKHOLE_CHANCE_PERCENT = 98;
	private static final int FIREBALL_CHANCE_PERCENT = 70;
	private static final int SHIBOLEET_CHANCE_PERCENT = 60;
	private static final int MISSILE_CHANCE_PERCENT = 50;
	
	private static final int BLACKHOLE_NUMBER = 1;
	private static final int FIREBALL_NUMBER = 3;
	private static final int SHIBOLEET_NUMBER = 2;
	private static final int MISSILE_NUMBER = 10;
	
	private static final Random RANDOM = new Random();
	private static final BonusCollisionBehavior COLLISION_BEHAVIOR = new BonusCollisionBehavior();
	
	public static Bonus createBonus(World world, float x, float y, EntityContainer ec) {
		
		Objects.requireNonNull(world);
		
		float shapeX = CoordinateConverter.toMeterX(Foundation.RESOURCES.getTexture(TextureLoader.BONUS_WEAPON_MISSILE).getWidth() / 2);
		float shapeY = CoordinateConverter.toMeterY(Foundation.RESOURCES.getTexture(TextureLoader.BONUS_WEAPON_MISSILE).getHeight() / 2);
		
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
		fixture.filter.categoryBits = Collisionable.BONUS_TYPE;
		fixture.filter.maskBits = MASK;
		
		Body body = world.createBody(bodyDef);
		body.createFixture(fixture);
		
		Bonus bonus;
		if(isBlackHoleBonus()) {
		
			bonus = new AbstractBonus(body, Foundation.RESOURCES.getTexture(TextureLoader.BONUS_WEAPON_BLACKHOLE), ec, ec, COLLISION_BEHAVIOR) {
				
				@Override
				public int getWeapon() {
					return Weapons.BLACKHOLE_ID;
				}
				
				@Override
				public int getNumber() {
					return BLACKHOLE_NUMBER;
				}

			};
			
		} else if(isFireballBonus()) {
			
			bonus = new AbstractBonus(body, Foundation.RESOURCES.getTexture(TextureLoader.BONUS_WEAPON_FIREBALL), ec, ec, COLLISION_BEHAVIOR) {
				
				@Override
				public int getWeapon() {
					return Weapons.FIREBALL_ID;
				}
				
				@Override
				public int getNumber() {
					return FIREBALL_NUMBER;
				}
				
			};
			
		} else if(isShiboleetBonus()) {
			
			bonus = new AbstractBonus(body, Foundation.RESOURCES.getTexture(TextureLoader.BONUS_WEAPON_SHIBOLEET), ec, ec, COLLISION_BEHAVIOR) {
				
				@Override
				public int getWeapon() {
					return Weapons.SHIBOLEET_ID;
				}
				
				@Override
				public int getNumber() {
					return SHIBOLEET_NUMBER;
				}
				
			};
			
		} else if(isMissileBonus()) {
			
			bonus = new AbstractBonus(body, Foundation.RESOURCES.getTexture(TextureLoader.BONUS_WEAPON_MISSILE), ec, ec, COLLISION_BEHAVIOR) {
				
				@Override
				public int getWeapon() {
					return Weapons.MISSILE_ID;
				}
				
				@Override
				public int getNumber() {
					return MISSILE_NUMBER;
				}
				
			};
			
		} else {
			bonus = null;
			world.destroyBody(body);
		}

		if(bonus != null) {
			
			bonus.moveBy(new float[]{0.0f, 0.0f, 2.0f});
			
			body.setUserData(bonus);
		}
		
		return bonus;
	}
	
	private static boolean isBlackHoleBonus() {
		return getChance() > BLACKHOLE_CHANCE_PERCENT;
	}
	
	private static boolean isFireballBonus() {
		return getChance() > FIREBALL_CHANCE_PERCENT;
	}
	
	private static boolean isShiboleetBonus() {
		return getChance() > SHIBOLEET_CHANCE_PERCENT;
	}
	
	private static boolean isMissileBonus() {
		return getChance() > MISSILE_CHANCE_PERCENT;
	}
	
	private static int getChance() {
		return RANDOM.nextInt(100);
	}
	
}
