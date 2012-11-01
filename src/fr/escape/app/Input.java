package fr.escape.app;

import java.util.Objects;

import fr.umlv.zen2.MotionEvent;
import fr.umlv.zen2.MotionEvent.Kind;

// TODO Comment
public class Input {
	
	private final MotionEvent event;
	
	public Input(MotionEvent event) {
		Objects.requireNonNull(event);
		this.event = event;
	}
	
	public int getX() {
		return event.getX();
	}
	
	public int getY() {
		return event.getY();
	}
	
	public Kind getKind() {
		if(event.getKind() == Kind.ACTION_DOWN) {
			;
		}
		return event.getKind();
	}
	
}
