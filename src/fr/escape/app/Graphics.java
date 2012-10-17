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
import java.awt.Image;
import java.awt.Rectangle;

import fr.umlv.zen2.ApplicationContext;
import fr.umlv.zen2.ApplicationRenderCode;

// TODO Comment this class
public final class Graphics {
	
	private final static int MINIMUM_WAKEUP_TIME = 4;
	private final static int MAXIMUM_WAKEUP_TIME = 32;
	
	private final RenderListener listener;
	private final Batch batch;
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
		this.batch = new Batch();
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
						
						Render render = batch.pop();
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
	 * Draws a rectangle with the bottom left corner at x,y having the width and height of the texture.
	 * 
	 * @param texture
	 * @param x
	 * @param y
	 */
//	public void draw(Texture texture, float x, float y) {
//		
//	}
//	
	
	public void draw(final Image texture, final int x, final int y) {
		batch.push(new Render() {

			@Override
			protected void render() {
				getGraphics().drawImage(texture, x, y, null);
			}

		});
	}
	
	/**
	 * Draws a rectangle with the bottom left corner at x,y and stretching the region to cover the given width and height.
	 * 
	 * @param texture
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
//	public draw(Texture texture, float x, float y, float width, float height) {
//		
//	}
	
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
		batch.push(new Render() {
			
			@Override
			protected void render() {
				getGraphics().setPaint(color);
				getGraphics().setFont(font);
				getGraphics().drawString(message, x, y);
			}
			
		});
	}

	private Font getDefaultFont() {
		return new Font("Arial", Font.PLAIN, 14);
	}
	
	private Color getDefaultColor() {
		return new Color(0);
	}
}
