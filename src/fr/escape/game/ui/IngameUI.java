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

import fr.escape.app.Input;
import fr.escape.app.Overlay;

public final class IngameUI extends AbstractOverlay {
	
	private final List<Overlay> overlays;
	
	public IngameUI() {
		overlays = new ArrayList<>();
	}
	
	public boolean add(Overlay overlay) {
		return overlays.add(overlay);
	}
	
	public boolean remove(Overlay overlay) {
		return overlays.remove(overlay);
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
		super.show();
		for(Overlay o : overlays) {
			o.show();
		}
	}

	@Override
	public void hide() {
		super.hide();
		for(Overlay o : overlays) {
			o.hide();
		}
	}

	@Override
	public boolean contains(Input touch) {
		
		for(Overlay o : overlays) {
			if(o.contains(touch)) {
				return true;
			}
		}
		
		return false;
	}
}
