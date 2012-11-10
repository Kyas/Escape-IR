/*****************************************************************************
 * 
 * Copyright 2012 See AUTHORS file.
 * 
 * This file is part of Escape-IR.
 * 
 * Escape-IR is free software: you can redistribute it and/or modify
 * it under the terms of the zlib license. See the COPYING file.
 * 
 *****************************************************************************/

package fr.escape.resources;

import java.awt.Font;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

import fr.escape.app.Foundation;
import fr.escape.graphics.Texture;
import fr.escape.resources.font.FontLoader;
import fr.escape.resources.texture.TextureLoader;

/**
 * <p>
 * Create a Index of available Resource and load them in memory if needed.
 * 
 * <p>
 * You cannot unload them after that.
 * 
 * <p>
 * For loading: simply use <i>get...(...)</i> 
 * 
 * <p>
 * <b>Note:</b> After instantiate this Object; You <b>HAVE TO</b> call <b>load()</b>
 *
 */
public final class Resources {
	
	static final String TAG = Resources.class.getSimpleName();
	
	private final HashMap<String, FontLoader> fontLoader;
	private final HashMap<String, TextureLoader> textureLoader;

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
		textureLoader = new HashMap<String, TextureLoader>();
		loaded = false;
	}
	
	/**
	 * Create and Load {@link ResourcesLoader} in List
	 */
	public void load() {
		if(loaded == false) {
			
			// Load Font
			loadFont(FontLoader.VISITOR_ID, 18.0f);
			
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
			
			loadTexture(TextureLoader.SHIP_SWING);
			loadTexture(TextureLoader.SHIP_SWING_1);
			loadTexture(TextureLoader.SHIP_SWING_2);
			loadTexture(TextureLoader.SHIP_SWING_3);
			loadTexture(TextureLoader.SHIP_SWING_4);
			loadTexture(TextureLoader.SHIP_SWING_5);
			loadTexture(TextureLoader.SHIP_SWING_6);
			loadTexture(TextureLoader.SHIP_SWING_7);
			loadTexture(TextureLoader.SHIP_SWING_8);
			loadTexture(TextureLoader.SHIP_SWING_9);
			
		}
		
		loaded = true;
	}
	
	/**
	 * Load and return Font from {@link ResourcesLoader} 
	 * 
	 * @param name Font name
	 * @return Font
	 * @throws NoSuchElementException
	 */
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
	
	/**
	 * Load and return Texture from {@link ResourcesLoader}
	 * 
	 * @param name Texture name
	 * @return Texture
	 * @throws NoSuchElementException
	 */
	public Texture getTexture(String name) throws NoSuchElementException {
		Objects.requireNonNull(name);
		checkIfLoaded();
		try {
			
			TextureLoader loader = textureLoader.get(name);
			return loader.load();
			
		} catch(Exception e) {
			NoSuchElementException exception = new NoSuchElementException("Cannot load the given Texture: "+name);
			exception.initCause(e);
			throw exception;
		}
	}
	
	/**
	 * Create a FontLoader for the given Font name.
	 * 
	 * @param fontID Font name
	 * @param size Font size
	 * @return FontLoader which will load the Font
	 */
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
	
	/**
	 * Create a TextureLoader for the given Texture name.
	 * 
	 * @param textureID Texture name
	 * @return TextureLoader which will load the Texture
	 */
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
	
	/**
	 * Create a TextureLoader and add it for a given Texture name.
	 * 
	 * @param textureID Texture name
	 */
	private void loadTexture(String textureID) {
		textureLoader.put(textureID, createTextureLoader(textureID));
	}
	
	/**
	 * Create a FontLoader and add it for a given Font name and size.
	 * 
	 * @param fontID Font name
	 * @param size Font size
	 */
	private void loadFont(String fontID, float size) {
		fontLoader.put(fontID, createFontLoader(fontID, size));
	}
	
	/**
	 * <p>
	 * Check if the {@link Resources} object is loaded.<br>
	 * (ie: {@link Resources#load()} has been called once).
	 * 
	 * <p>
	 * Otherwise, throw an {@link IllegalStateException}.
	 */
	private void checkIfLoaded() {
		if(loaded == false) {
			throw new IllegalStateException("You must load all resources before using them. Use load()");
		}
	}
	
}
