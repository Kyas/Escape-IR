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

import fr.escape.Escape;

/**
 * <p>
 * A class which delegates rendering to a {@link Screen}. 
 * 
 * <p>
 * Allowing multiple screens for a Game.
 */
public abstract class Game {
	
	private Screen screen;

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

	public void resume () {
		if (screen != null) {
			screen.resume();
		}
	}

	public void render () {
		if(screen != null) {
			// TODO finish it!
			//screen.render(Gdx.graphics.getDeltaTime());
		}
		Escape.graphics.render();
	}
	
	public void resize(int width, int height) {
		if (screen != null) {
			screen.resize(width, height);
		}
	}

	public void setScreen(Screen screen) {
		
		if (this.screen != null) {
			this.screen.hide();
		}
		
		this.screen = screen;
		
		screen.show();
		// TODO finish it!
		//screen.resize(600, 800);
	}

	/**
	 * Return the current active {@link Screen}.
	 *
	 * @return active {@link Screen}.
	 */
	public Screen getScreen () {
		return screen;
	}
}
