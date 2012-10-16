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

public final class Graphics {
	
	private final int width;
	private final int heigt;
	
	private long lastRender;
	private int rawFps;
	private int smothFps;
	
	public Graphics(int width, int height) {
		this.width = width;
		this.heigt = height;
		this.lastRender = System.currentTimeMillis();
		this.rawFps = 0;
		this.smothFps = 0;
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
	 * @return The average number of frames per second
	 */
	public int getFramesPerSecond() {
		return smothFps;
	}
	
	public void render() {
		
		long currentRender = System.currentTimeMillis();
		rawFps++;
		
		if((lastRender / 1000) < (currentRender / 1000)) {
			smothFps = (int) ((0.1 * smothFps) + (0.9 * rawFps));
			rawFps = 0;
		}
		
		lastRender = currentRender;
	}
	
}
