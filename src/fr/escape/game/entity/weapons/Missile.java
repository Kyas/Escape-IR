package fr.escape.game.entity.weapons;

import org.jbox2d.dynamics.World;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.weapons.shot.Shot;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public final class Missile implements Weapon {
	
	private final Texture drawable;
	
	public Missile() {
		drawable = Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_MISSILE);
	}

	@Override
	public Texture getDrawable() {
		return drawable;
	}

	public int getAmmunition() {
		return 999;
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
	public void draw(Graphics graphics) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load(World world, EntityContainer ec, float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fire(World world, EntityContainer ec, float[] velocity) {
		// TODO Auto-generated method stub
		
	}
	
}