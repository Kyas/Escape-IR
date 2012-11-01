package fr.escape.resources.drawable;

import fr.escape.graphics.Texture;

public final class SWin extends DrawableLoader {

	@Override
	public Texture load() throws Exception {
		return new Texture(getPath().resolve("swin.png").toFile());
	}
	
}
