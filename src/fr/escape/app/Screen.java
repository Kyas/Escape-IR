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

/**
 * Represents one of many game screens, like main menu, or settings etc...
 * 
 * @see Game
 */
public interface Screen {
	
	/**
	 * Called when the screen should render itself.
	 * 
	 * @param delta The time in seconds since the last render.
	 */
	public void render(float delta);

	/**
	 * @see Game#resize(int, int)
	 */
	public void resize(int width, int height);

	/** 
	 * Called when this screen becomes the current screen for a {@link Game}.
	 */
	public void show();

	/** 
	 * Called when this screen is no longer the current screen for a {@link Game}.
	 */
	public void hide();

	/**
	 * @see ApplicationListener#pause()
	 */
	public void pause();

	/** 
	 * @see ApplicationListener#resume()
	 */
	public void resume();

	/** 
	 * Called when this screen should release itself.
	 */
	public void dispose();
}
