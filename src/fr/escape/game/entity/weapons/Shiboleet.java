package fr.escape.game.entity.weapons;

import java.util.Objects;

import org.jbox2d.dynamics.World;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.weapons.shot.Shot;
import fr.escape.game.entity.weapons.shot.ShotFactory;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public final class Shiboleet implements Weapon {

	private final Texture drawable;
	private int ammunition;
	
	private Shot shot;
	
	public Shiboleet(int defaultAmmunition) {
		drawable = Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_SHIBOLEET);
		ammunition = defaultAmmunition;
	}
	
	@Override
	public Texture getDrawable() {
		return drawable;
	}

	@Override
	public int getAmmunition() {
		return ammunition;
	}

	@Override
	public boolean isEmpty() {
		return getAmmunition() <= 0;
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
	public boolean load(World world, EntityContainer ec, float x, float y) {
		if(!isEmpty() && shot == null) {
			
			shot = ShotFactory.createShiboleetShot(world, x, y, ec);
			shot.receive(Shot.MESSAGE_LOAD);
			
			return true;
		}
		
		return false;
	}

	@Override
	public boolean fire(World world, EntityContainer ec, float[] velocity) {

		if(shot != null) {
			
			// TODO
			shot.setPosition(Foundation.GRAPHICS,velocity);
			
			Objects.requireNonNull(world).step(1.0f/60.0f, 6, 2);
			
			Objects.requireNonNull(ec).push(shot);
			shot.receive(Shot.MESSAGE_FIRE);
			
			// TODO Apply Speed and Angle
			shot.receive(Shot.MESSAGE_CRUISE);
			
			shot = null;
			
			return true;
		}
		
		return false;
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
