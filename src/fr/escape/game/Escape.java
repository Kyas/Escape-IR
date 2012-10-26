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

package fr.escape.game;

import fr.escape.app.Game;
import fr.escape.app.Overlay;
import fr.escape.game.screen.Splash;
import fr.escape.game.screen.Error;
import fr.escape.game.ui.IngameUI;
import fr.escape.game.ui.UIHighscore;
import fr.escape.game.ui.UIWeapons;

public class Escape extends Game {

	private Splash splash;
	private Error error;
	private IngameUI ingameUI;
	private HighscoreUpdater highscoreUpdater;
	
	/**
	 * @see Game#create()
	 */
	@Override
	public void create() {
		try {
			
			// Create Screen
			splash = new Splash(this);
			// Other Screen if any ...
			
			// Create Game Components
			ingameUI = new IngameUI();
			highscoreUpdater = new HighscoreUpdater();
			
			UIHighscore uHighscore = new UIHighscore(this);
			UIWeapons uWeapons = new UIWeapons(this);
			
			highscoreUpdater.add(uHighscore);
			ingameUI.add(uHighscore);
			ingameUI.add(uWeapons);
			
			setScreen(splash);
			
		} catch(Exception e) {
			error = new Error(this);
			getActivity().error("Escape", "Exception raised during create()", e);
			setScreen(error);
		}
		
	}
	
	/**
	 * @see Game#render()
	 */
	@Override
	public void render() {
		super.render();
		if(ingameUI != null) {
			ingameUI.render(getGraphics().getDeltaTime());
		}
	}

	/**
	 * <p>
	 * Return the Overlay used as Ingame UI.
	 * 
	 * @return {@link Overlay}
	 */
	public Overlay getOverlay() {
		return ingameUI;
	}
	
	/**
	 * <p>
	 * Return the {@link HighscoreUpdater} used Ingame.
	 * 
	 * @return {@link HighscoreUpdater}
	 */
	public HighscoreUpdater getHighscoreUpdater() {
		return highscoreUpdater;
	}
	
}
