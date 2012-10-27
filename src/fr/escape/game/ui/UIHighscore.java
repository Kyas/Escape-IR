package fr.escape.game.ui;

import java.awt.Color;
import java.awt.Font;

import fr.escape.app.Game;
import fr.escape.game.Receiver;

public class UIHighscore extends AbstractOverlay implements Receiver {

	private static final int TOP_PADDING = 20;
	private static final int LEFT_MARGIN = 10;
	
	private final Game game;
	private final Font font;
	private final Color color;
	
	private int highscore;
	
	public UIHighscore(Game game) {
		this.game = game;
		this.font = game.getResources().getFont("visitor");
		this.color = Color.WHITE;
		this.highscore = 0;
	}
	
	private int getTopPadding() {
		return TOP_PADDING;
	}
	
	private int getLeftMargin() {
		return LEFT_MARGIN;
	}

	@Override
	public void render(long delta) {
		if(isVisible()) {
			game.getGraphics().draw("Highscore: "+highscore, getLeftMargin(), getTopPadding(), font, color);
		}
	}

	@Override
	public void receive(int message) {
		setHighscore(message);
	}
	
	private void setHighscore(int highscore) {
		this.highscore = highscore;
	}

}
