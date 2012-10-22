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

package fr.escape.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import fr.escape.graphics.GraphicsBatchRender;
import fr.escape.graphics.GraphicsPoolRender;
import fr.escape.graphics.render.GraphicsRender;
import fr.escape.graphics.render.GraphicsStringRender;
import fr.escape.graphics.texture.Texture;
import fr.escape.graphics.texture.TextureOperator;
import fr.umlv.zen2.ApplicationContext;
import fr.umlv.zen2.ApplicationRenderCode;

// TODO Comment this class
public final class Graphics {
	
	private final static int MINIMUM_WAKEUP_TIME = 4;
	private final static int MAXIMUM_WAKEUP_TIME = 32;
	
	private final RenderListener listener;
	private final GraphicsBatchRender batch;
	private final GraphicsPoolRender pool;
	private final int width;
	private final int heigt;
	private final int displayFps;
	
	private long lastRender;
	private int rawFps;
	private int smoothFps;
	private int wakeUp;

	public Graphics(RenderListener listener, Configuration configuration) {
		
		this.width = configuration.width;
		this.heigt = configuration.height;
		this.displayFps = configuration.fps;
		
		this.listener = listener;
		this.lastRender = System.currentTimeMillis();
		this.rawFps = 0;
		this.smoothFps = 0;
		this.wakeUp = 25;
		
		this.batch = GraphicsBatchRender.getInstance();
		this.pool = GraphicsPoolRender.getInstance();
	}
	
	/** 
	 * @return The width in pixels of the display.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return The height in pixels of the display.
	 */
	public int getHeight() {
		return heigt;
	}

	/** 
	 * @return The time span between the current frame and the last frame in seconds.
	 */
	public long getDeltaTime() {
		return System.currentTimeMillis() - lastRender;
	}
	
	/** 
	 * @return The average number of frames per second.
	 */
	public int getFramesPerSecond() {
		return smoothFps;
	}
	
	public int getRequestedFramesPerSecond() {
		return displayFps;
	}
	
	public int getNextWakeUp() {
		return wakeUp;
	}
	
	public void render(final ApplicationContext context) {
		
		listener.render();
		
		context.render(new ApplicationRenderCode() {
			@Override
			public void render(Graphics2D graphics) {
				
				// Flush and clear previous drawing
				graphics.fill(new Rectangle(0, 0, getWidth(), getHeight()));
				
				while(!batch.isEmpty()) {
					try {
						
						GraphicsRender render = batch.pop();
						render.setGraphics(graphics);
						render.run();
						
					} catch(Throwable e) {
						Foundation.activity.error("Graphics - render", "Exception raised for this batch", e);
					}
				}
				
			}
		});
		
		long currentRender = System.currentTimeMillis();
		rawFps++;
		
		if((lastRender / 1000) < (currentRender / 1000)) {
			updateFramesPerSecond();
			updateWait();
		}
		
		lastRender = currentRender;
		
	}
	
	/**
	 * Update the average number of frames per second.
	 */
	private void updateFramesPerSecond() {
		smoothFps = (int) ((0.1 * smoothFps) + (0.9 * rawFps));
		rawFps = 0;
	}
	
	private void updateWait() {
		
		if(((getFramesPerSecond() < getRequestedFramesPerSecond()) &&
				(wakeUp > MINIMUM_WAKEUP_TIME)) || 
				((getFramesPerSecond() > getRequestedFramesPerSecond()) &&
				(wakeUp < MAXIMUM_WAKEUP_TIME))) {
			
			int factor = getRequestedFramesPerSecond() - getFramesPerSecond();
			wakeUp = 1000 / (getRequestedFramesPerSecond() + factor);
			
			if(wakeUp < MINIMUM_WAKEUP_TIME) {
				wakeUp = MINIMUM_WAKEUP_TIME;
			}
			
			if(wakeUp > MAXIMUM_WAKEUP_TIME) {
				wakeUp = MAXIMUM_WAKEUP_TIME;
			}
			
		}
		
	}

	/**
	 * <p>
	 * Draws a rectangle with the top left corner at x,y having the width and height of the texture.
	 * 
	 * @param texture Texture used for rendering
	 * @param x Position X in Display Screen
	 * @param y Position Y in Display Screen
	 */
	public void draw(final Texture texture, final int x, final int y) {
		draw(texture, x, y, texture.getWidth(), texture.getHeight());
	}
	
	/**
	 * <p>
	 * Draws a rectangle with the top left corner at x,y and stretching the region to cover the given width and height.
	 * 
	 * @param texture Texture used for rendering
	 * @param x Starting Position X in Display Screen
	 * @param y Starting Position Y in Display Screen
	 * @param width Ending Position X in Display Screen
	 * @param height Ending Position Y in Display Screen
	 */
	public void draw(final Texture texture, final int x, final int y, final int width, final int height) {
		draw(texture, x, y, width, height, 0, 0, width, height);
	}
	
	/**
	 * <p>
	 * Draws a rectangle with the top left corner at x,y having the given width and height in pixels. 
	 * 
	 * <p>
	 * The portion of the Texture given by srcX, srcY and srcWidth, srcHeight are used.
	 * 
	 * @param texture Texture used for rendering
	 * @param x Position X in Display Screen
	 * @param y Position Y in Display Screen
	 * @param srcX Starting Position X in Texture
	 * @param srcY Starting Position Y in Texture
	 * @param srcWidth Ending Position X in Texture
	 * @param srcHeight Ending Position Y in Texture
	 */
	public void draw(final Texture texture, final int x, final int y, final int srcX, final int srcY, final int srcWidth, final int srcHeight) {
		this.draw(texture, x, y, srcWidth - srcX, srcHeight - srcY, srcX, srcY, srcWidth, srcHeight);
	}
	
	/**
	 * <p>
	 * Draws a rectangle with the top left corner at x,y having the given width and height in pixels. 
	 * 
	 * <p>
	 * The portion of the Texture given by srcX, srcY and srcWidth, srcHeight is used.
	 * 
	 * @param texture Texture used for rendering
	 * @param x Starting Position X in Display Screen
	 * @param y Starting Position Y in Display Screen
	 * @param width Ending Position X in Display Screen
	 * @param height Ending Position Y in Display Screen
	 * @param srcX Starting Position X in Texture
	 * @param srcY Starting Position Y in Texture
	 * @param srcWidth Ending Position X in Texture
	 * @param srcHeight Ending Position Y in Texture
	 */
	public void draw(final Texture texture, final int x, final int y, final int width, final int height, final int srcX, final int srcY, final int srcWidth, final int srcHeight) {
		
		batch.push(new GraphicsRender() {
			
			@Override
			protected void render() {
				texture.draw(getGraphics(), x, y, width, height, srcX, srcY, srcWidth, srcHeight);
			}

		});
	}
	
	public void draw(final TextureOperator texture, final int x, final int y, final int width, final int height) {
		
		batch.push(new GraphicsRender() {

			@Override
			protected void render() {
				texture.draw(getGraphics(), x, y, width, height);
			}
			
		});
		
	}
	
	public void draw(String message, int x, int y) {
		draw(message, x, y, getDefaultFont());
	}
	
	public void draw(String message, int x, int y, Font font) {
		draw(message, x, y, font, getDefaultColor());
	}
	
	public void draw(String message, int x, int y, Color color) {
		draw(message, x, y, getDefaultFont(), getDefaultColor());
	}
	
	public void draw(final String message, final int x, final int y, final Font font, final Color color) {
		batch.push(pool.fetchGraphicsStringRender().setX(x).setY(y).setMessage(message).setColor(color).setFont(font));
	}

	private Font getDefaultFont() {
		return new Font("Arial", Font.PLAIN, 14);
	}
	
	private Color getDefaultColor() {
		return new Color(0);
	}
}
