package fr.escape.game;

import java.util.ArrayList;
import java.util.List;

public final class HighscoreUpdater {

	private final List<HighscoreListener> listeners;
	
	public HighscoreUpdater() {
		listeners = new ArrayList<>();
	}
	
	public boolean add(HighscoreListener listener) {
		return listeners.add(listener);
	}
	
	public boolean remove(HighscoreListener listener) {
		return listeners.remove(listener);
	}
	
	public void update(int highscore) {
		for(HighscoreListener listener : listeners) {
			listener.update(highscore);
		}
	}
	
	public static interface HighscoreListener {
		public void update(int highscore);
	}

}
