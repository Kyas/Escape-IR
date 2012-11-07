package fr.escape.game.entity.weapons;

import fr.escape.app.Foundation;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public final class BlackHole implements Weapon {
	
	private final Texture drawable;
	private int ammunition;
	
	public BlackHole(int defaultAmmunition) {
		drawable = Foundation.resources.getTexture(TextureLoader.WEAPON_BLACKHOLE);
		ammunition = defaultAmmunition;
	}

	@Override
	public Texture getDrawable() {
		return drawable;
	}

	public int getAmmunition() {
		return ammunition;
	}
	
	@Override
	public boolean isEmpty() {
		return getAmmunition() <= 0;
	}
	
}
