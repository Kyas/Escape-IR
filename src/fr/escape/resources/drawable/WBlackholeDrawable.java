package fr.escape.resources.drawable;

import fr.escape.graphics.Texture;

public class WBlackholeDrawable extends DrawableLoader {

	@Override
	public Texture load() throws Exception {
		return new Texture(getPath().resolve("wblackhole.png").toFile());
	}

}
