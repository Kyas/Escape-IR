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

import org.jbox2d.dynamics.World;

import fr.escape.graphics.RenderListener;
import fr.escape.input.EventListener;
import fr.escape.resources.Resources;

/**
 * <p>
 * A class which delegates rendering to a {@link Screen}. 
 * 
 * <p>
 * Allowing multiple screens for a Game.
 */
public abstract class Game implements RenderListener, EventListener {
	
	private Screen screen;
	private World world;
	
	public abstract void create();
	
//	public void dispose() {
//		if(screen != null) {
//			screen.hide();
//		}
//	}
//
//	public void pause() {
//		if(screen != null) {
//			screen.pause();
//		}
//	}
//
//	public void resume() {
//		if(screen != null) {
//			screen.resume();
//		}
//	}

	public void render() {
		if(screen != null) {
			screen.render(getGraphics().getDeltaTime());
		}
	}
	
//	public void resize(int width, int height) {
//		if(screen != null) {
//			screen.resize(width, height);
//		}
//	}

	public void setScreen(Screen screen) {
		
		if(this.screen != null) {
			this.screen.hide();
		}
		
		this.screen = screen;
		
		screen.show();
		//screen.resize(Foundation.graphics.getWidth(), Foundation.graphics.getHeight());
	}

	/**
	 * Return the current active {@link Screen}.
	 *
	 * @return active {@link Screen}.
	 */
	public Screen getScreen() {
		return screen;
	}
	
	/**
	 * Return the {@link Activity} which created this Game.
	 * 
	 * @return {@link Activity}.
	 */
	public Activity getActivity() {
		return Foundation.activity;
	}
	
	/**
	 * Return the {@link Graphics} for the Game.
	 * 
	 * @return {@link Graphics}
	 */
	public Graphics getGraphics() {
		return Foundation.graphics;
	}

	/**
	 * Return the {@link Resources} for the Game.
	 * 
	 * @return {@link Resources}
	 */
	public Resources getResources() {
		return Foundation.resources;
	}
	
	/**
	 * 
	 */
	public boolean touch(Input i) {
		return true;
	}
	
	/**
	 * 
	 */
	public boolean move(Input i) {
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public World getWorld() {
		return world;
	}
	
	/**
	 * 
	 * @param world
	 */
	public void setWorld(World world) {
		this.world = world;
	}
}
