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

package fr.escape.game.screen;

import fr.escape.app.Screen;
import fr.escape.game.Escape;

public class Splash implements Screen {

	private final Escape game;
	
	public Splash(Escape game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		
		game.getActivity().error("Splash - render", String.valueOf(delta));
		
		game.getGraphics().draw("Delta :"+delta, 10, 20);
		game.getGraphics().draw("Fps :"+game.getGraphics().getFramesPerSecond(), 10, 34);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}
