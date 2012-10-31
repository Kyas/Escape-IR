package fr.escape.weapons;

import fr.escape.app.Foundation;
import fr.escape.graphics.Texture;

public class BlackHole implements Weapon {
	
	private final Texture drawable;
	
	public BlackHole() {
		
		drawable = Foundation.resources.getDrawable("wblackhole");
		
	}

	@Override
	public Texture getDrawable() {
		return drawable;
	}

	public int getAmmunition() {
		return 655;
	}
	
}
