package fr.escape.game.entity.weapons;

import fr.escape.app.Graphics;
import fr.escape.game.entity.Updateable;
import fr.escape.game.entity.weapons.shot.Shot;
import fr.escape.graphics.Texture;

public interface Weapon extends Updateable {	
	
	public Texture getDrawable();
	
	public int getAmmunition();
	
	public boolean isEmpty();
	
	public Shot getShot();
	
	public boolean load(float x, float y);
	
	public boolean reload(int number);
	
	public boolean unload();
	
	public boolean fire(float[] velocity, boolean isPlayer);

	/**
	 * Call {@link Updateable#update(Graphics, long)} on the loaded Shot if any
	 * 
	 * @see Updateable#update(Graphics, long)
	 */
	@Override
	public void update(Graphics graphics, long delta);

	public boolean reset();
	
}
