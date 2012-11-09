package fr.escape.game.entity.ships;

import java.util.List;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import fr.escape.app.Graphics;
import fr.escape.game.entity.Drawable;
import fr.escape.game.entity.Entity;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.Moveable;
import fr.escape.game.entity.weapons.Weapon;

// TODO Comment
public interface Ship extends Moveable, Drawable, Entity {
	
	public void setPosition(World world,Graphics graphics,float[] val);
	
	public List<Weapon> getAllWeapons();
	
	public void setActiveWeapon(int which);
	
	public Weapon getActiveWeapon();
	
	public Body getBody();
	
	public float getX();
	
	public float getY();
	
	public boolean isWeaponLoaded();
	
	public boolean loadWeapon(World w, EntityContainer ec);
	
	public boolean fireWeapon(World world, EntityContainer ec, float[] velocity);
	
}
