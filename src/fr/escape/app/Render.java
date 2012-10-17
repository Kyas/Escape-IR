package fr.escape.app;

import java.awt.Graphics2D;
import java.util.Objects;

abstract class Render implements Runnable {

	private Graphics2D g2d;
	
	@Override
	public void run() {
		Objects.requireNonNull(g2d);
		render();
	}
	
	public void setGraphics(Graphics2D graphics) {
		g2d = graphics;
	}
	
	protected Graphics2D getGraphics() {
		return g2d;
	}
	
	protected abstract void render();

}
