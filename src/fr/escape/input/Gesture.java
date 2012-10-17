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

package fr.escape.input;

import java.util.List;
import fr.umlv.zen2.MotionEvent;

public interface Gesture {
	public boolean accept(MotionEvent start, List<MotionEvent> events);
	public void move();
}
