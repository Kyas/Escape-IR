package fr.escape.input;

import fr.escape.app.Input;

public interface EventListener {
	public boolean touch(Input i);
	public boolean move(Input i);
}
