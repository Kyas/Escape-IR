package fr.escape.resources.drawable;

import fr.escape.graphics.Texture;

public class WFireballDrawable extends DrawableLoader {

	@Override
	public Texture load() throws Exception {
		return new Texture(getPath().resolve("wfireball.png").toFile());
	}
	
}
