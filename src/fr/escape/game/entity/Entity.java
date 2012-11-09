package fr.escape.game.entity;

import org.jbox2d.dynamics.Body;

import fr.escape.app.Graphics;

public interface Entity {

	public void update(Graphics graphics, long delta);
	
	public Body getBody();
	
}
