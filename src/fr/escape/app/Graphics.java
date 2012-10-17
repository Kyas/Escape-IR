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


// TODO Comment this class
public final class Graphics {
	
	private final static int MINIMUM_WAKEUP_TIME = 4;
	private final static int MAXIMUM_WAKEUP_TIME = 32;
	
	private final RenderListener listener;
	private final int width;
	private final int heigt;
	private final int displayFps;
	
	private long lastRender;
	private int rawFps;
	private int smoothFps;
	private int wakeUp;

	public Graphics(RenderListener render, Configuration configuration) {
		this.width = configuration.width;
		this.heigt = configuration.height;
		this.displayFps = configuration.fps;
		this.listener = render;
		this.lastRender = System.currentTimeMillis();
		this.rawFps = 0;
		this.smoothFps = 0;
		this.wakeUp = 25;
	}
	
	/** 
	 * @return The width in pixels of the display.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return The height in pixels of the display.
	 */
	public int getHeight() {
		return heigt;
	}

	/** 
	 * @return The time span between the current frame and the last frame in seconds.
	 */
	public long getDeltaTime() {
		return System.currentTimeMillis() - lastRender;
	}
	
	/** 
	 * @return The average number of frames per second.
	 */
	public int getFramesPerSecond() {
		return smoothFps;
	}
	
	public int getRequestedFramesPerSecond() {
		return displayFps;
	}
	
	public int getNextWakeUp() {
		return wakeUp;
	}
	
	public void render() {
		
		listener.render();
		
		long currentRender = System.currentTimeMillis();
		rawFps++;
		
		if((lastRender / 1000) < (currentRender / 1000)) {
			updateFramesPerSecond();
			updateWait();
			E.activity.log("Graphics - FPS", String.valueOf(getFramesPerSecond()));
		}
		
		lastRender = currentRender;
	}
	
	/**
	 * Update the average number of frames per second.
	 */
	private void updateFramesPerSecond() {
		smoothFps = (int) ((0.1 * smoothFps) + (0.9 * rawFps));
		rawFps = 0;
	}
	
	private void updateWait() {
		
		if(((getFramesPerSecond() < getRequestedFramesPerSecond()) &&
				(wakeUp > MINIMUM_WAKEUP_TIME)) || 
				((getFramesPerSecond() > getRequestedFramesPerSecond()) &&
				(wakeUp < MAXIMUM_WAKEUP_TIME))) {
			
			int factor = getRequestedFramesPerSecond() - getFramesPerSecond();
			wakeUp = 1000 / (getRequestedFramesPerSecond() + factor);
			
			if(wakeUp < MINIMUM_WAKEUP_TIME) {
				wakeUp = MINIMUM_WAKEUP_TIME;
			}
			
			if(wakeUp > MAXIMUM_WAKEUP_TIME) {
				wakeUp = MAXIMUM_WAKEUP_TIME;
			}
			
		}
		
	}
}
