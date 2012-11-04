package fr.escape.game.entity.ships;

import java.util.List;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import fr.escape.app.Graphics;
import fr.escape.game.entity.Drawable;
import fr.escape.game.entity.Moveable;
import fr.escape.game.entity.weapons.Weapon;

// TODO Comment
public interface Ship extends Moveable, Drawable {
	
	public void setPosition(World world,Graphics graphics,float[] val);
	
	public List<Weapon> getAllWeapons();
	
	public void setActiveWeapon(int which);
	
	public int getActiveWeapon();
	
	public Body getBody();
	
	public float getX();
	
	public float getY();
}
