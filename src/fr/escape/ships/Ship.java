package fr.escape.ships;

import java.util.List;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import fr.escape.app.Graphics;
import fr.escape.weapons.Weapon;

public interface Ship {
	public void setPosition(World world,Graphics graphics,float[] val);
	public void draw(Graphics graphics);
	public List<Weapon> getAllWeapons();
	public void setActiveWeapon(int which);
	public int getActiveWeapon();
	public Body getBody();
	public float getX();
	public float getY();
}
