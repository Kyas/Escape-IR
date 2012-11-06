package fr.escape.game.entity.weapons;

import fr.escape.app.Foundation;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public final class Missile implements Weapon {
	
	private final Texture drawable;
	
	public Missile() {
		drawable = Foundation.resources.getTexture(TextureLoader.WEAPON_MISSILE);
	}

	@Override
	public Texture getDrawable() {
		return drawable;
	}

	public int getAmmunition() {
		return 999;
	}
	
}