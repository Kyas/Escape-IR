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

package fr.escape.resources.scenario;

import java.nio.file.Path;

import fr.escape.game.scenario.Scenario;
import fr.escape.graphics.Texture;
import fr.escape.resources.ResourcesLoader;

/**
 * <p>
 * A {@link ResourcesLoader} for {@link Stage} and {@link Scenario}.
 * 
 */
public abstract class ScenarioLoader implements ResourcesLoader<Texture> {
	
	public static final String EARTH_1 = "earth_1.scn";
	
	@Override
	public Path getPath() {
		return PATH.resolve("scenario");
	}
	
}