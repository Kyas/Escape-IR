package fr.escape.graphics.texture;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.Objects;

import javax.imageio.ImageIO;

import fr.escape.app.Disposable;
import fr.escape.graphics.Drawable;

public final class Texture implements Disposable, Drawable {

	private final BufferedImage image;
	
	public Texture(File file) throws IOException {
		Objects.requireNonNull(file);
		image = ImageIO.read(file);
	}
	
	public int getWidth() {
		return image.getWidth();
	}
	
	public int getHeight() {
		return image.getHeight();
	}
	
	public void dispose() {
		image.flush();
	}
	
	private Image getImage() {
		return image;
	}

	@Override
	public void draw(Graphics2D graphics, int x, int y, int width, int height,
			int srcX, int srcY, int srcWidth, int srcHeight) {

		graphics.drawImage(getImage(), x, y, width, height, srcX, srcY, srcWidth, srcHeight, null);
	}
	
}
