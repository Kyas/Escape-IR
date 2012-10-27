package fr.escape.ships;

import java.util.List;

import fr.escape.weapons.Weapon;

//TODO just a test
public interface Ship {
	
	public void setPosition(int x,int y);
	
	
	public List<Weapon> getAllWeapons();
	
	public boolean setActiveWeapon(int which);
	public int getActiveWeapon();
	
}
