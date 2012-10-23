package fr.escape.game.ui;

import java.awt.Color;
import java.awt.Font;

import fr.escape.app.Game;
import fr.escape.graphics.RenderListener;

public class UIHighscore implements RenderListener {

	private static final int TOP_PADDING = 20;
	private static final int LEFT_MARGIN = 10;
	
	private final Game game;
	private final Font font;
	private final Color color;
	
	public UIHighscore(Game game) {
		this.game = game;
		this.font = game.getResources().getFont("visitor2");
		this.color = Color.WHITE;
	}
	
	private int getTopPadding() {
		return TOP_PADDING;
	}
	
	private int getLeftMargin() {
		return LEFT_MARGIN;
	}

	@Override
	public void render() {
		game.getGraphics().draw("Highscore: "+Integer.MAX_VALUE, getLeftMargin(), getTopPadding(), font, color);
	}

}
