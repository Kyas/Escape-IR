package fr.escape.resources;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface ResourcesLoader<T> {
	
	public static final Path PATH = Paths.get("src", "fr", "escape", "resources");
	
	public T load() throws Exception;
	
	public Path getPath();
	
}
