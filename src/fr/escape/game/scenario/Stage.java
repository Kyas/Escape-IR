package fr.escape.game.scenario;

import org.jbox2d.dynamics.World;

import fr.escape.game.entity.EntityContainer;

public interface Stage {

	public void start();
	public void update(int time);
	public void reset();
	public void boss(World world, EntityContainer container);
	
}
