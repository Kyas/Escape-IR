package fr.escape.weapons;

import fr.escape.app.Foundation;
import fr.escape.graphics.Texture;

public final class Missile implements Weapon {
	
	private final Texture drawable;
	
	public Missile() {
		drawable = Foundation.resources.getDrawable("wmissile");
	}

	@Override
	public Texture getDrawable() {
		return drawable;
	}

	public int getAmmunition() {
		return 999;
	}
	
}