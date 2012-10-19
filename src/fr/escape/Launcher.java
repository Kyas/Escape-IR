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

package fr.escape;

import fr.escape.app.Activity;
import fr.escape.app.Configuration;
import fr.escape.app.Foundation;
import fr.escape.game.Escape;

/**
 * Default Launcher/Main for Escape-IR
 */
public final class Launcher {

	private static final String TAG = "Launcher";
	
	/**
	 * Default Constructor for launching the Game
	 */
	public Launcher() {
		(new Activity(new Escape(), new Configuration())).setLogLevel(Activity.LOG_DEBUG);
	}
	
	/**
	 * Default Entry Point
	 * 
	 * @param args Options, if any.
	 */
	public static void main(String[] args) {
		new Launcher();
		Foundation.activity.debug(TAG, "Initialize Launcher");
	}
	
}
