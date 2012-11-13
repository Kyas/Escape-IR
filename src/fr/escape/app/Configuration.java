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
 * <p>
 * Configuration Object for {@link Graphics} and {@link Activity}.
 * 
 */
public final class Configuration {

	private static int MIN_FPS = 20;
	private static int MAX_FPS = 40;
	
	private final int width;
	private final int height;
	private final String title;
	private final int fps;
	
	public Configuration() {
		this(400, 600);
	}
	
	public Configuration(int width, int height) {
		this(width, height, Integer.MAX_VALUE);
	}
	
	public Configuration(int width, int height, int fps) {
		this("Escape-IR", width, height, fps);
	}
	
	public Configuration(String title, int width, int height, int fps) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.fps = Math.min(Math.max(fps, MIN_FPS), MAX_FPS);
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getTitle() {
		return title;
	}

	public int getFps() {
		return fps;
	}
}
