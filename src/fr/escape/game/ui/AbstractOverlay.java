package fr.escape.game.ui;

import fr.escape.app.Input;
import fr.escape.app.Overlay;
import fr.escape.input.EventListener;

public abstract class AbstractOverlay implements Overlay {
	
	private boolean isVisible;
	
	public AbstractOverlay() {
		isVisible = false;
	}
	
	/**
	 * @see Overlay#show()
	 */
	@Override
	public void show() {
		isVisible = true;
	}

	/**
	 * @see Overlay#hide()
	 */
	@Override
	public void hide() {
		isVisible = false;
	}

	/**
	 * <p>
	 * Return true if the Overlay is Visible.
	 * 
	 * @return If the Overlay is Visible
	 */
	public boolean isVisible() {
		return isVisible;
	}
	
	/**
	 * @see EventListener#touch(Input)
	 */
	@Override
	public boolean touch(Input touch) {
		return false;
	}

	/**
	 * @see EventListener#move(Input)
	 */
	@Override
	public boolean move(Input move) {
		return false;
	}
	
}
