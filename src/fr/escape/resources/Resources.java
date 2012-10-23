package fr.escape.resources;

import java.awt.Font;
import java.util.HashMap;
import java.util.NoSuchElementException;

import fr.escape.graphics.Texture;
import fr.escape.resources.drawable.BErrorDrawable;
import fr.escape.resources.drawable.DrawableLoader;
import fr.escape.resources.font.FontLoader;
import fr.escape.resources.font.Visitor;

public final class Resources {

	private final HashMap<String, FontLoader> fontLoader;
	private final HashMap<String, DrawableLoader> drawableLoader;

	public Resources() {

		fontLoader = new HashMap<String, FontLoader>();
		drawableLoader = new HashMap<String, DrawableLoader>();
		
		init();
	}
	
	private void init() {
		fontLoader.put("visitor", new Visitor());
		drawableLoader.put("berror", new BErrorDrawable());
	}
	
	public Font getFont(String name) throws NoSuchElementException {
		try {
			
			FontLoader loader = fontLoader.get(name);
			return loader.load();
			
		} catch(Exception e) {
			NoSuchElementException exception = new NoSuchElementException("Cannot load the given Font: "+name);
			exception.initCause(e);
			throw exception;
		}
	}
	
	public Texture getDrawable(String name) throws NoSuchElementException {
		try {
			
			DrawableLoader loader = drawableLoader.get(name);
			return loader.load();
			
		} catch(Exception e) {
			NoSuchElementException exception = new NoSuchElementException("Cannot load the given Texture: "+name);
			exception.initCause(e);
			throw exception;
		}
	}

	public static Resources getInstance() {
		return ResourcesHolder.INSTANCE;
	}
	
	private static class ResourcesHolder {
		public static final Resources INSTANCE = new Resources();
	}
	
}
