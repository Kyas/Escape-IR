package fr.escape.game.entity.weapons;

import fr.escape.graphics.Texture;

public interface Weapon {	
	public Texture getDrawable();
	public int getAmmunition();
	public boolean isEmpty();
}
