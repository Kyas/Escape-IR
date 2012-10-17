package fr.escape.input;

import java.util.List;
import fr.umlv.zen2.MotionEvent;

public interface Gesture {
	public boolean accept(MotionEvent start,List<MotionEvent> events);
	public void move();
}
