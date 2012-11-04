package fr.escape.game.entity.weapons;

import fr.escape.graphics.Texture;

public interface Weapon {
	
	final static int WIDTH = 40;
	final static int HEIGHT = 40;
	
	public Texture getDrawable();
	public int getAmmunition();
	
}
