package fr.escape.game.ui;

import java.awt.Color;
import java.awt.Font;

import fr.escape.app.Game;
import fr.escape.app.Overlay;
import fr.escape.game.HighscoreUpdater.HighscoreListener;

public class UIHighscore implements Overlay, HighscoreListener {

	private static final int TOP_PADDING = 20;
	private static final int LEFT_MARGIN = 10;
	
	private final Game game;
	private final Font font;
	private final Color color;
	
	private int highscore;
	private boolean isVisible;
	
	public UIHighscore(Game game) {
		this.game = game;
		this.font = game.getResources().getFont("visitor");
		this.color = Color.WHITE;
		this.highscore = 0;
		this.isVisible = false;
	}
	
	private int getTopPadding() {
		return TOP_PADDING;
	}
	
	private int getLeftMargin() {
		return LEFT_MARGIN;
	}

	@Override
	public void render(long delta) {
		if(isVisible) {
			game.getGraphics().draw("Highscore: "+highscore, getLeftMargin(), getTopPadding(), font, color);
		}
	}

	@Override
	public void show() {
		isVisible = true;
	}

	@Override
	public void hide() {
		isVisible = false;
	}

	@Override
	public void update(int highscore) {
		this.highscore = highscore;
	}

}
