package fr.escape.game.entity.weapons;

import java.util.Objects;

import org.jbox2d.dynamics.World;

import fr.escape.app.Foundation;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.weapons.shot.Shot;
import fr.escape.game.entity.weapons.shot.ShotFactory;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public final class BlackHole implements Weapon {
	
	private final Texture drawable;
	private int ammunition;
	
	private Shot shot;
	
	public BlackHole(int defaultAmmunition) {
		drawable = Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_BLACKHOLE);
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
	public void load(World world,float x,float y,EntityContainer ec) {
		shot = ShotFactory.createBlackholeShot(world,x,y,ec);
	}
	
	@Override
	public void fire(World world,float[] velocity) {
		Objects.requireNonNull(world);
		shot.setPosition((int)velocity[0],(int)velocity[1]);
		world.step(1.0f/60.0f,6,2);
	}
	
	@Override
	public Shot getShot() {
		return shot;
	}
}
