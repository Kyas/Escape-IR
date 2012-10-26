package fr.escape.app;

import fr.umlv.zen2.MotionEvent;
import fr.umlv.zen2.MotionEvent.Kind;

public class Input {
	private final MotionEvent event;
	
	public Input(MotionEvent event) {
		this.event = event;
	}
	
	public int getX() {
		return event.getX();
	}
	
	public int getY() {
		return event.getY();
	}
	
	public Kind getKind() {
		return event.getKind();
	}

}
