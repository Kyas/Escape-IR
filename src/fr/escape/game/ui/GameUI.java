package fr.escape.game.ui;

import fr.escape.app.Game;
import fr.escape.graphics.RenderListener;

public final class GameUI implements RenderListener {
	
	private final UIHighscore highscore;
	private final UIWeapons weapons;
	private boolean isVisible;
	
	public GameUI(Game game) {
		this.highscore = new UIHighscore(game);
		this.weapons = new UIWeapons(game);
		this.isVisible = false;
	}

	@Override
	public void render() {
		if(isVisible()) {
			highscore.render();
			weapons.render();
		}
	}
	
	public void setVisible(boolean visible) {
		isVisible = visible;
	}
	
	public boolean isVisible() {
		return isVisible;
	}
}
