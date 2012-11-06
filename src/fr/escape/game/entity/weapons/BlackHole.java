package fr.escape.game.entity.weapons;

import fr.escape.app.Foundation;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public final class BlackHole implements Weapon {
	
	private final Texture drawable;
	
	public BlackHole() {
		
		drawable = Foundation.resources.getTexture(TextureLoader.WEAPON_BLACKHOLE);
		
	}

	@Override
	public Texture getDrawable() {
		return drawable;
	}

	public int getAmmunition() {
		return 655;
	}
	
}
