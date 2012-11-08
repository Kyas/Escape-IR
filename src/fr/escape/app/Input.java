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

import java.util.Objects;

import fr.umlv.zen2.MotionEvent;
import fr.umlv.zen2.MotionEvent.Kind;

/**
 * <p>
 * A Wrapper for User Input.
 * 
 * <p>
 * This class can handle input such as Mouse, Network and/or Keyboard.
 */
public final class Input {
	
	/**
	 * Mouse Input.
	 */
	private final MotionEvent event;
	
	/**
	 * Default Constructor
	 * 
	 * @param event Mouse Input Event
	 */
	public Input(MotionEvent event) {
		Objects.requireNonNull(event);
		this.event = event;
	}
	
	/**
	 * Get Mouse Input X Coordinate.
	 * 
	 * @return X coordinate.
	 */
	public int getX() {
		return event.getX();
	}
	
	/**
	 * Get Mouse Input Y Coordinate.
	 * 
	 * @return Y coordinate.
	 */
	public int getY() {
		return event.getY();
	}
	
	/**
	 * Get Mouse Input Kind.
	 * 
	 * @return Mouse Kind
	 */
	public Kind getKind() {
		if(event.getKind() == Kind.ACTION_DOWN) {
			;
		}
		return event.getKind();
	}
	
}
