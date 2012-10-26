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

package fr.escape.game.ui;

import java.util.ArrayList;
import java.util.List;

import fr.escape.app.Overlay;

public final class IngameUI implements Overlay {
	
	private final List<Overlay> overlays;
	private boolean isVisible;
	
	public IngameUI() {
		overlays = new ArrayList<>();
		this.isVisible = false;
	}
	
	public boolean isVisible() {
		return isVisible;
	}

	@Override
	public void render(long delta) {
		if(isVisible()) {
			for(Overlay o : overlays) {
				o.render(delta);
			}
		}
	}

	@Override
	public void show() {
		isVisible = true;
		for(Overlay o : overlays) {
			o.show();
		}
	}

	@Override
	public void hide() {
		isVisible = false;
		for(Overlay o : overlays) {
			o.hide();
		}
	}
}
