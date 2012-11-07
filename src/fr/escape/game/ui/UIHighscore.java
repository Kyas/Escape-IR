/*****************************************************************************
 * 
 * Copyright 2012 See AUTHORS file.
 * 
 * This file is part of Escape-IR.
 * 
 * Escape-IR is free software: you can redistribute it and/or modify
 * it under the terms of the zlib license. See the COPYING file.
 * 
 *****************************************************************************/

package fr.escape.game.ui;

import java.awt.Color;
import java.awt.Font;

import fr.escape.app.Game;
import fr.escape.game.User;
import fr.escape.game.message.Receiver;
import fr.escape.resources.font.FontLoader;

/**
 * <p>
 * An Overlay which show the User Highscore at the given time.
 * 
 * <p>
 * Information are received from {@link User}.
 */
public final class UIHighscore extends AbstractOverlay implements Receiver {

	/**
	 * Rendering
	 */
	private static final int TOP_PADDING = 20;
	private static final int LEFT_MARGIN = 10;
	
	private final Game game;
	private final Font font;
	private final Color color;
	
	/**
	 * Highscore to show
	 */
	private int highscore;
	
	public UIHighscore(Game game) {
		this.game = game;
		this.font = game.getResources().getFont(FontLoader.VISITOR_ID);
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
			game.getGraphics().draw("FPS: "+game.getGraphics().getFramesPerSecond(), getLeftMargin(), getTopPadding() + 15, font, color);
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
