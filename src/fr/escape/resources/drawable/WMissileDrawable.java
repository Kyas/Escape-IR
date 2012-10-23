package fr.escape.resources.drawable;

import fr.escape.graphics.Texture;

public class WMissileDrawable extends DrawableLoader {

	@Override
	public Texture load() throws Exception {
		return new Texture(getPath().resolve("wmissile.png").toFile());
	}

}
