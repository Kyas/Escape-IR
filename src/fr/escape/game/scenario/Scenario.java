package fr.escape.game.scenario;

import fr.escape.game.entity.EntityContainer;

public interface Scenario {
	
	public int getID();
	
	public int getStart();
	
	public boolean setContainer(EntityContainer container);
	
	public boolean hasFinished();
	
	/**
	 * Execute Action in the Scenario for a specific time.
	 * 
	 * @param time Seconds elapsed since beginning of the Game.
	 */
	public void action(int time);
	
}
