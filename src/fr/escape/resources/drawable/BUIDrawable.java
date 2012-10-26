package fr.escape.resources.drawable;

import fr.escape.graphics.Texture;

public class BUIDrawable extends DrawableLoader {

	@Override
	public Texture load() throws Exception {
		return new Texture(getPath().resolve("bui.png").toFile());
	}

}