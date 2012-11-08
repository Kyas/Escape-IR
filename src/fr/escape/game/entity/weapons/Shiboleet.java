package fr.escape.game.entity.weapons;

import fr.escape.app.Foundation;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public final class Shiboleet implements Weapon {

	private final Texture drawable;
	private int ammunition;
	
	public Shiboleet(int defaultAmmunition) {
		drawable = Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_SHIBOLEET);
		ammunition = defaultAmmunition;
	}
	
	@Override
	public Texture getDrawable() {
		return drawable;
	}

	@Override
	public int getAmmunition() {
		return ammunition;
	}

	@Override
	public boolean isEmpty() {
		return getAmmunition() <= 0;
	}
	
}
