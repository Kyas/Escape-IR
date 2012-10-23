package fr.escape.resources.drawable;

import java.nio.file.Path;

import fr.escape.graphics.Texture;
import fr.escape.resources.ResourcesLoader;

public abstract class DrawableLoader implements ResourcesLoader<Texture> {
	
	public Path getPath() {
		return PATH.resolve("drawable");
	}
	
}