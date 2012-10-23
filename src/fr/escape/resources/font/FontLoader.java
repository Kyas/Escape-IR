package fr.escape.resources.font;

import java.awt.Font;
import java.nio.file.Path;

import fr.escape.resources.ResourcesLoader;

public abstract class FontLoader implements ResourcesLoader<Font> {
	
	public Path getPath() {
		return PATH.resolve("font");
	}
	
}
