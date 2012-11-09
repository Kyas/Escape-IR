package fr.escape.game.entity.bonus;

import java.awt.Rectangle;
import java.util.Random;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
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
	
	public static Bonus createBonus(EntityContainer ec) {
		
		if(isBlackHoleBonus()) {
		
			return new AbstractBonus(Foundation.RESOURCES.getTexture(TextureLoader.BONUS_WEAPON_BLACKHOLE), ec) {
				
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
			
			return new AbstractBonus(Foundation.RESOURCES.getTexture(TextureLoader.BONUS_WEAPON_FIREBALL), ec) {
				
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
			
			return new AbstractBonus(Foundation.RESOURCES.getTexture(TextureLoader.BONUS_WEAPON_SHIBOLEET), ec) {
				
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
			
			return new AbstractBonus(Foundation.RESOURCES.getTexture(TextureLoader.BONUS_WEAPON_MISSILE), ec) {
				
				@Override
				public int getWeapon() {
					return Weapons.MISSILE_ID;
				}
				
				@Override
				public int getNumber() {
					return MISSILE_NUMBER;
				}
				
			};
			
		}
		
		return null;
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
		private final Runnable runnable;
		
		private int x;
		private int y;
		
		public AbstractBonus(Texture drawable, EdgeNotifier notifier) {
			
			this.drawable = drawable;
			this.notifier = notifier;
			
			this.runnable = new Runnable() {
				
				@Override
				public void run() {
					moveBy(0, 1);
				}
				
			};
			
			this.x = 0;
			this.y = 0;
		}
		
		@Override
		public void moveBy(int x, int y) {
			this.setPosition(this.x + x, this.y + y);
		}

		@Override
		public void rotateBy(int angle) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void setPosition(int x, int y) {
			
			this.x = x;
			this.y = y;
			
			if(!notifier.isInside(getEdge())) {
				notifier.edgeReached(this);
			}
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
			
			int x = this.x - (drawable.getWidth() / 2);
			int y = this.y - (drawable.getHeight() / 2);
			
			graphics.draw(drawable, x, y);
		}
		
		@Override
		public void update(Graphics graphics, long delta) {
			draw(graphics);
			Foundation.ACTIVITY.post(runnable);
		}
	}
	
}
