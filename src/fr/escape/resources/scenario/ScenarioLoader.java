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

import java.util.Objects;

import fr.escape.game.entity.ships.ShipFactory;
import fr.escape.game.scenario.Scenario;
import fr.escape.resources.ResourcesLoader;

/**
 * <p>
 * A {@link ResourcesLoader} for {@link Stage} and {@link Scenario}.
 * 
 */
public abstract class ScenarioLoader implements ResourcesLoader<Scenario> {
	
	public static final String EARTH_1 = "earth_1.scn";
	public static final String JUPITER_1 = "jupiter_1.scn";
	public static final String JUPITER_2 = "jupiter_2.scn";
	
	private ShipFactory factory;
	
	@Override
	public String getPath() {
		return PATH+"/scenario";
	}
	
	/**
	 * Add a Ship Factory for this Scenario.
	 * 
	 * @param sf Ship Factory
	 */
	public void addShipCreator(ShipFactory sf) {
		factory = Objects.requireNonNull(sf);
	}
	
	/**
	 * Get the Ship Factory for this Scenario.
	 * 
	 * @return Ship Factory
	 */
	protected ShipFactory getShipCreator() {
		return factory;
	}
	
}