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

package fr.escape.app;

import fr.escape.E;

/**
 * <p>
 * A class which delegates rendering to a {@link Screen}. 
 * 
 * <p>
 * Allowing multiple screens for a Game.
 */
public abstract class Game implements RenderListener {
	
	private Screen screen;

	public abstract void create();
	
	public void dispose() {
		if (screen != null) {
			screen.hide();
		}
	}

	public void pause() {
		if (screen != null) {
			screen.pause();
		}
	}

	public void resume() {
		if (screen != null) {
			screen.resume();
		}
	}

	public void render() {
		if(screen != null) {
			screen.render(E.graphics.getDeltaTime());
		}
	}
	
	public void resize(int width, int height) {
		if (screen != null) {
			screen.resize(width, height);
		}
	}

	public void setScreen(Screen screen) {
		
		if(this.screen != null) {
			this.screen.hide();
		}
		
		this.screen = screen;
		
		screen.show();
		screen.resize(E.graphics.getWidth(), E.graphics.getHeight());
	}

	/**
	 * Return the current active {@link Screen}.
	 *
	 * @return active {@link Screen}.
	 */
	public Screen getScreen() {
		return screen;
	}
}
