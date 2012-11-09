package fr.escape.game.entity.weapons;

import org.jbox2d.dynamics.World;

import fr.escape.game.entity.Drawable;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.weapons.shot.Shot;
import fr.escape.graphics.Texture;

public interface Weapon extends Drawable {	
	public Texture getDrawable();
	public int getAmmunition();
	public boolean isEmpty();
	public Shot getShot();
	public void load(World world,float x,float y,EntityContainer ec);
	public void fire(World world,float[] velocity);
}
