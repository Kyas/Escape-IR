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

public class Escape extends Game {

	private Splash splash;
	private Error error;
	private Overlay ingameUI;
	
	/**
	 * @see Game#create()
	 */
	@Override
	public void create() {
		try {
			
			splash = new Splash(this);
			// Other Screen if any ...
			
			ingameUI = new IngameUI();
			
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

	public Overlay getOverlay() {
		return ingameUI;
	}

}
