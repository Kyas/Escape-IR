package fr.escape.game.entity.bonus;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.weapons.Weapons;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public final class BonusFactory {

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
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.DYNAMIC;
		
		CircleShape shape = new CircleShape();
		shape.m_radius = CoordinateConverter.toMeterX(Foundation.RESOURCES.getTexture(TextureLoader.BONUS_WEAPON_MISSILE).getHeight() / 2);
		
		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.density = 0.5f;
		fixture.friction = 0.0f;
		fixture.restitution = 0.0f;
		fixture.filter.categoryBits = 0x000F;
		fixture.filter.maskBits = 0x0002;
		
		Body body = world.createBody(bodyDef);
		body.createFixture(fixture);
		
		Bonus bonus;
		if(isBlackHoleBonus()) {
		
			bonus = new AbstractBonus(body, Foundation.RESOURCES.getTexture(TextureLoader.BONUS_WEAPON_BLACKHOLE), ec) {
				
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
			
			bonus = new AbstractBonus(body, Foundation.RESOURCES.getTexture(TextureLoader.BONUS_WEAPON_FIREBALL), ec) {
				
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
			
			bonus = new AbstractBonus(body, Foundation.RESOURCES.getTexture(TextureLoader.BONUS_WEAPON_SHIBOLEET), ec) {
				
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
			
			bonus = new AbstractBonus(body, Foundation.RESOURCES.getTexture(TextureLoader.BONUS_WEAPON_MISSILE), ec) {
				
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
			
			ArrayList<Object> userData = new ArrayList<>(2);
			
			bonus.moveBy(new float[]{0.0f, 0.0f, 2.0f});
			
			userData.add(0, "Bonus");
			userData.add(1, bonus);
			body.setUserData(userData);
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
		
		private final Texture drawable;
		private final EdgeNotifier notifier;
		
		private final Body body;
		
		private int x;
		private int y;
		
		public AbstractBonus(Body body,Texture drawable, EdgeNotifier notifier) {
			
			this.body = body;
			this.drawable = drawable;
			this.notifier = notifier;
			this.x = 0;
			this.y = 0;
			
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
			// TODO
			/*this.x = x;
			this.y = y;
			
			if(!notifier.isInside(getEdge())) {
				notifier.edgeReached(this);
			}*/
		}
		
		@Override
		public void setRotation(int angle) {
			throw new UnsupportedOperationException();
		}
		
		public Rectangle getEdge() {
			return new Rectangle(x - (drawable.getWidth() / 2), y - (drawable.getHeight() / 2), drawable.getWidth(), drawable.getHeight());
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
			if(!notifier.isInside(getEdge())) {
				notifier.edgeReached(this);
			}
		}
		
		@Override
		public Body getBody() {
			return body;
		}

		@Override
		public void toDestroy() {
			// TODO 
		}
	}
	
}
