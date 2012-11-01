package fr.escape.resources.drawable;

import fr.escape.graphics.Texture;

public final class WPMissileDrawable extends DrawableLoader {

	@Override
	public Texture load() throws Exception {
		return new Texture(getPath().resolve("wpmissile.png").toFile());
	}

}