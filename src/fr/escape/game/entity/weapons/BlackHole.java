package fr.escape.game.entity.weapons;

import java.util.Objects;

import org.jbox2d.dynamics.World;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.weapons.shot.Shot;
import fr.escape.game.entity.weapons.shot.ShotFactory;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public final class BlackHole implements Weapon {
	
	private final Texture drawable;
	private final EntityContainer container;
	private int ammunition;
	
	private Shot shot;
	
	public BlackHole(EntityContainer eContainer, int defaultAmmunition) {
		drawable = Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_BLACKHOLE);
		container = Objects.requireNonNull(eContainer);
		ammunition = defaultAmmunition;
	}

	@Override
	public Texture getDrawable() {
		return drawable;
	}

	public int getAmmunition() {
		return ammunition;
	}
	
	@Override
	public boolean isEmpty() {
		return getAmmunition() <= 0;
	}

	@Override
	public boolean load(World world, EntityContainer ec, float x, float y) {
		if(!isEmpty() && shot == null) {
			
			shot = ShotFactory.createBlackholeShot(world, x, y, CoordinateConverter.toMeterX(drawable.getHeight()), ec);
			shot.receive(Shot.MESSAGE_LOAD);
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean fire(float[] velocity) {

		if(shot != null) {
			
			// TODO
			shot.setPosition(Foundation.GRAPHICS, velocity);
			
			Objects.requireNonNull(container).push(shot);
			shot.receive(Shot.MESSAGE_FIRE);
			
			// TODO Apply Speed and Angle
			shot.receive(Shot.MESSAGE_CRUISE);
			
			shot = null;
			ammunition--;
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public Shot getShot() {
		return shot;
	}

	@Override
	public void draw(Graphics graphics) {
		if(shot != null) {
			shot.draw(graphics);
		}
	}

	@Override
	public boolean unload() {
		
		if(shot != null) {
			shot.receive(Shot.MESSAGE_DESTROY);
			shot = null;
		}
		
		return false;
	}
	
}
