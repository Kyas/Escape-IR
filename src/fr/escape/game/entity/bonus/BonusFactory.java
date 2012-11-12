package fr.escape.game.entity.bonus;

import java.awt.Rectangle;
import java.util.Objects;
import java.util.Random;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.User;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.Entity;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;
import fr.escape.game.entity.weapons.Weapons;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public final class BonusFactory {
	private static final int MASK = 0x0002;

	private static final int BLACKHOLE_CHANCE_PERCENT = 98;
	private static final int FIREBALL_CHANCE_PERCENT = 70;
	private static final int SHIBOLEET_CHANCE_PERCENT = 60;
	private static final int MISSILE_CHANCE_PERCENT = 50;
	
	private static final int BLACKHOLE_NUMBER = 1;
	private static final int FIREBALL_NUMBER = 3;
	private static final int SHIBOLEET_NUMBER = 2;
	private static final int MISSILE_NUMBER = 10;
	
	private static final Random RANDOM = new Random();
	
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
		fixture.filter.categoryBits = 0x000F;
		fixture.filter.maskBits = MASK;
		
		Body body = world.createBody(bodyDef);
		body.createFixture(fixture);
		
		Bonus bonus;
		if(isBlackHoleBonus()) {
		
			bonus = new AbstractBonus(body, Foundation.RESOURCES.getTexture(TextureLoader.BONUS_WEAPON_BLACKHOLE), ec, ec) {
				
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
			
			bonus = new AbstractBonus(body, Foundation.RESOURCES.getTexture(TextureLoader.BONUS_WEAPON_FIREBALL), ec, ec) {
				
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
			
			bonus = new AbstractBonus(body, Foundation.RESOURCES.getTexture(TextureLoader.BONUS_WEAPON_SHIBOLEET), ec, ec) {
				
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
			
			bonus = new AbstractBonus(body, Foundation.RESOURCES.getTexture(TextureLoader.BONUS_WEAPON_MISSILE), ec, ec) {
				
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
	
	private static abstract class AbstractBonus implements Bonus {
		
		private static int COEFFICIENT = 3;
		private static String TAG = AbstractBonus.class.getSimpleName();
		
		private final Texture drawable;
		private final EdgeNotifier eNotifier;
		private final KillNotifier kNotifier;
		
		private final Body body;
		
		public AbstractBonus(Body body,Texture drawable, EdgeNotifier eNotifier, KillNotifier kNotifier) {
			
			this.body = body;
			this.drawable = drawable;
			this.eNotifier = eNotifier;
			this.kNotifier = kNotifier;
			
		}

		@Override
		public void rotateBy(int angle) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void moveBy(float[] velocity) {
			if(body.isActive()) {
				body.setLinearVelocity(new Vec2(velocity[1], velocity[2]));
			}
		}
		
		@Override
		public void moveTo(float x, float y) {
			float distanceX = x - getX();
			float distanceY = y - getY();
			
			float max = Math.max(Math.abs(distanceX), Math.abs(distanceY));
			float coeff = COEFFICIENT / max;
			
			body.setLinearVelocity(new Vec2(distanceX * coeff, distanceY * coeff));
		}
		
		@Override
		public void setRotation(int angle) {
			throw new UnsupportedOperationException();
		}
		
		public Rectangle getEdge() {
			int x = CoordinateConverter.toPixelX(getX());
			int y = CoordinateConverter.toPixelY(getY());
			
			return new Rectangle(x - (drawable.getWidth() / 2), y - (drawable.getHeight() / 2), drawable.getWidth(), drawable.getHeight());
		}
		
		private float getX() {
			return body.getPosition().x;
		}

		private float getY() {
			return body.getPosition().y;
		}

		@Override
		public void draw(Graphics graphics) {
			
			int x = CoordinateConverter.toPixelX(body.getPosition().x) - (drawable.getWidth() / 2);
			int y = CoordinateConverter.toPixelY(body.getPosition().y) - (drawable.getHeight() / 2);
			
			graphics.draw(drawable, x, y);
		}
		
		@Override
		public void update(Graphics graphics, long delta) {
			draw(graphics);
			if(!eNotifier.isInside(getEdge())) {
				eNotifier.edgeReached(this);
			}
		}
		
		@Override
		public Body getBody() {
			return body;
		}

		@Override
		public void toDestroy() {
			kNotifier.destroy(this);
		}
		
		@Override
		public void collision(User user, int whoami, Entity e, int whois) {
			switch(whois) {
				case PLAYER_TYPE: {
					user.addBonus(getWeapon(), getNumber());
					this.toDestroy();
					break;
				}
				default: {
					Foundation.ACTIVITY.error(TAG, "Unknown touch contact {"+this+", "+e+"}");
					this.toDestroy();
					break;
				}
		}
		}
		
	}
	
}
