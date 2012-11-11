package fr.escape.game.entity.weapons;

import fr.escape.game.entity.Drawable;
import fr.escape.game.entity.weapons.shot.Shot;
import fr.escape.graphics.Texture;

public interface Weapon extends Drawable {	
	
	public Texture getDrawable();
	
	public int getAmmunition();
	
	public boolean isEmpty();
	
	public Shot getShot();
	
	public boolean load(float x, float y);
	
	public boolean unload();
	
	public boolean fire(float[] velocity);
}
