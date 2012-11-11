package fr.escape.game.entity.bonus;

import fr.escape.game.entity.Drawable;
import fr.escape.game.entity.Entity;
import fr.escape.game.entity.Moveable;

/**
 * 
 */
// TODO Comment
public interface Bonus extends Entity, Moveable, Drawable {
	
	public int getWeapon();
	public int getNumber();
	
}
