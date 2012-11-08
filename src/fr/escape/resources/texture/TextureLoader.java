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

package fr.escape.resources.texture;

import java.nio.file.Path;

import fr.escape.graphics.Texture;
import fr.escape.resources.ResourcesLoader;

/**
 * <p>
 * A {@link ResourcesLoader} for {@link Texture}.
 * 
 */
public abstract class TextureLoader implements ResourcesLoader<Texture> {
	
	public static final String BACKGROUND_ERROR = "berror.png";
	public static final String BACKGROUND_LOST = "blost.png";
	public static final String BACKGROUND_MENU = "bmenu.png";
	
	public static final String WEAPON_UI_DISABLED = "wuidisabled.png";
	public static final String WEAPON_UI_ACTIVATED = "wuiactivated.png";
	
	public static final String WEAPON_MISSILE = "wmissile.png";
	public static final String WEAPON_FIREBALL = "wfireball.png";
	public static final String WEAPON_SHIBOLEET = "wshiboleet.png";
	public static final String WEAPON_BLACKHOLE = "wblackhole.png";
	
	public static final String WEAPON_MISSILE_SHOT = "wsmissile.png";
	public static final String WEAPON_FIREBALL_CORE_SHOT = "wsfireballcore.png";
	public static final String WEAPON_FIREBALL_RADIUS_SHOT = "wsfireballradius.png";
	public static final String WEAPON_SHIBOLEET_SHOT = "wsshiboleet.png";
	public static final String WEAPON_BLACKHOLE_CORE_SHOT = "wsblackholecore.png";
	public static final String WEAPON_BLACKHOLE_LEFT_SHOT = "wsblackholehl.png";
	public static final String WEAPON_BLACKHOLE_RIGHT_SHOT = "wsblackholehr.png";
	public static final String WEAPON_BLACKHOLE_EVENT_HORIZON_SHOT = "wsblackholeeh.png";
	
	public static final String BONUS_WEAPON_MISSILE = "bwmissile.png";
	public static final String BONUS_WEAPON_FIREBALL = "bwfireball.png";
	public static final String BONUS_WEAPON_SHIBOLEET = "bwshiboleet.png";
	public static final String BONUS_WEAPON_BLACKHOLE = "bwblackhole.png";
	
	// TODO REMOVE
	public static final String DEBUG_WIN = "swin.png";
	
	@Override
	public Path getPath() {
		return PATH.resolve("texture");
	}
	
}