package fr.escape.resources.drawable;

import fr.escape.graphics.Texture;

public class BErrorDrawable extends DrawableLoader {

	@Override
	public Texture load() throws Exception {
		return new Texture(getPath().resolve("berror.png").toFile());
	}

}
