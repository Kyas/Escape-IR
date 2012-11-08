package fr.escape.resources;

import java.awt.Font;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

import fr.escape.app.Foundation;
import fr.escape.graphics.Texture;
import fr.escape.resources.font.FontLoader;
import fr.escape.resources.texture.TextureLoader;

public final class Resources {
	
	static final String TAG = Resources.class.getSimpleName();
	
	private final HashMap<String, FontLoader> fontLoader;
	private final HashMap<String, TextureLoader> drawableLoader;

	/**
	 * Is all resources loaded in memory ?
	 */
	private boolean loaded;
	
	/**
	 * <p>
	 * Default Constructor for {@link Resources}.
	 * 
	 * <p>
	 * Don't forget to call load() after instantiation.
	 */
	public Resources() {
		fontLoader = new HashMap<String, FontLoader>();
		drawableLoader = new HashMap<String, TextureLoader>();
		loaded = false;
	}
	
	public void load() {
		if(loaded == false) {
			
			// Load Font
			fontLoader.put(FontLoader.VISITOR_ID, createFontLoader(FontLoader.VISITOR_ID, 18.0f));
			
			// Load Texture
			loadTexture(TextureLoader.BACKGROUND_ERROR);
			loadTexture(TextureLoader.BACKGROUND_LOST);
			loadTexture(TextureLoader.BACKGROUND_MENU);
			loadTexture(TextureLoader.WEAPON_UI_ACTIVATED);
			loadTexture(TextureLoader.WEAPON_UI_DISABLED);
			loadTexture(TextureLoader.WEAPON_BLACKHOLE);
			loadTexture(TextureLoader.WEAPON_FIREBALL);
			loadTexture(TextureLoader.WEAPON_MISSILE);
			loadTexture(TextureLoader.WEAPON_SHIBOLEET);
			loadTexture(TextureLoader.WEAPON_MISSILE_SHOT);
			loadTexture(TextureLoader.WEAPON_FIREBALL_CORE_SHOT);
			loadTexture(TextureLoader.WEAPON_FIREBALL_RADIUS_SHOT);
			loadTexture(TextureLoader.WEAPON_SHIBOLEET_SHOT);
			loadTexture(TextureLoader.WEAPON_BLACKHOLE_CORE_SHOT);
			loadTexture(TextureLoader.WEAPON_BLACKHOLE_LEFT_SHOT);
			loadTexture(TextureLoader.WEAPON_BLACKHOLE_RIGHT_SHOT);
			loadTexture(TextureLoader.WEAPON_BLACKHOLE_EVENT_HORIZON_SHOT);
			loadTexture(TextureLoader.BONUS_WEAPON_MISSILE);
			loadTexture(TextureLoader.BONUS_WEAPON_FIREBALL);
			loadTexture(TextureLoader.BONUS_WEAPON_SHIBOLEET);
			loadTexture(TextureLoader.BONUS_WEAPON_BLACKHOLE);
			
			loadTexture(TextureLoader.DEBUG_WIN);
			
		}
		
		loaded = true;
	}
	
	public Font getFont(String name) throws NoSuchElementException {
		Objects.requireNonNull(name);
		checkIfLoaded();
		try {
			
			FontLoader loader = fontLoader.get(name);
			return loader.load();
			
		} catch(Exception e) {
			NoSuchElementException exception = new NoSuchElementException("Cannot load the given Font: "+name);
			exception.initCause(e);
			throw exception;
		}
	}
	
	public Texture getTexture(String name) throws NoSuchElementException {
		Objects.requireNonNull(name);
		checkIfLoaded();
		try {
			
			TextureLoader loader = drawableLoader.get(name);
			return loader.load();
			
		} catch(Exception e) {
			NoSuchElementException exception = new NoSuchElementException("Cannot load the given Texture: "+name);
			exception.initCause(e);
			throw exception;
		}
	}
	
	private static FontLoader createFontLoader(final String fontID, final float size) {
		return new FontLoader() {

			private Font font;
			
			@Override
			public Font load() throws Exception {
				if(font == null) {
					Foundation.ACTIVITY.debug(TAG, "Load Font: "+fontID);
					font = Font.createFont(Font.TRUETYPE_FONT, getPath().resolve(fontID).toFile());
					font = font.deriveFont(size);
				}
				return font;
			}

		};
	}
	
	private static TextureLoader createTextureLoader(final String textureID) {
		return new TextureLoader() {
			
			private Texture texture = null;
			
			@Override
			public Texture load() throws Exception {
				if(texture == null) {
					Foundation.ACTIVITY.debug(TAG, "Load Texture: "+textureID);
					texture = new Texture(getPath().resolve(textureID).toFile());
				}
				return texture;
			}
			
		};
	}
	
	private void loadTexture(String textureID) {
		drawableLoader.put(textureID, createTextureLoader(textureID));
	}
	
	private void checkIfLoaded() {
		if(loaded == false) {
			throw new IllegalStateException("You must load all resources before using them. Use load()");
		}
	}
	
}
