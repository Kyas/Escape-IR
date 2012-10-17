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

	public final int width;
	public final int height;
	public final String title;
	public final int fps;
	
	public Configuration() {
		this(400, 800);
	}
	
	public Configuration(int width, int height) {
		this(width, height, 60);
	}
	
	public Configuration(int width, int height, int fps) {
		this("Escape-IR", width, height, fps);
	}
	
	public Configuration(String title, int width, int height, int fps) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.fps = fps;
	}
	
}
