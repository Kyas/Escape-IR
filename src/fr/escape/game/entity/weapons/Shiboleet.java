package fr.escape.game.entity.weapons;

import org.jbox2d.dynamics.World;

import fr.escape.app.Foundation;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.weapons.shot.Shot;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public final class Shiboleet implements Weapon {

	private final Texture drawable;
	private int ammunition;
	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void load(World world, float x, float y, EntityContainer ec) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fire(World world, float[] velocity) {
		// TODO Auto-generated method stub
		
	}
	
}
