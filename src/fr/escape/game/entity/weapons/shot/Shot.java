package fr.escape.game.entity.weapons.shot;

import org.jbox2d.dynamics.World;

import fr.escape.app.Graphics;
import fr.escape.game.entity.Drawable;
import fr.escape.game.entity.Entity;
import fr.escape.game.entity.Moveable;
import fr.escape.game.message.Receiver;

public interface Shot extends Drawable, Moveable, Receiver, Entity {
	public void setPosition(World world,Graphics graphics,float[] velocity);
}
