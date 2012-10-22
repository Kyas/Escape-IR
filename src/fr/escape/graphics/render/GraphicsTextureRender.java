package fr.escape.graphics.render;

import java.util.Objects;

import fr.escape.graphics.texture.Texture;

public class GraphicsTextureRender extends GraphicsRender {

	private Texture texture;
	private int x;
	private int y;
	private int width;
	private int height;
	private int srcX;
	private int srcY;
	private int srcWidth;
	private int srcHeight;
	
	public GraphicsTextureRender() {
		recycle();
	}
	
	@Override
	protected void render() {
		Objects.requireNonNull(getTexture());
		getTexture().draw(getGraphics(), getX(), getY(), getWidth(), getHeight(), getSrcX(), getSrcY(), getSrcWidth(), getSrcHeight());
	}

	@Override
	public void recycle() {
		texture = null;
		x = y = width = height = srcX = srcY = srcWidth = srcHeight = 0;
	}

	private Texture getTexture() {
		return texture;
	}

	private int getX() {
		return x;
	}

	private int getY() {
		return y;
	}

	private int getWidth() {
		return width;
	}

	private int getHeight() {
		return height;
	}

	private int getSrcX() {
		return srcX;
	}

	private int getSrcY() {
		return srcY;
	}

	private int getSrcWidth() {
		return srcWidth;
	}

	private int getSrcHeight() {
		return srcHeight;
	}

	public void setTexture(final Texture texture) {
		this.texture = texture;
	}

	public void setX(final int x) {
		this.x = x;
	}

	public void setY(final int y) {
		this.y = y;
	}

	public void setWidth(final int width) {
		this.width = width;
	}

	public void setHeight(final int height) {
		this.height = height;
	}

	public void setSrcX(final int srcX) {
		this.srcX = srcX;
	}

	public void setSrcY(final int srcY) {
		this.srcY = srcY;
	}

	public void setSrcWidth(final int srcWidth) {
		this.srcWidth = srcWidth;
	}

	public void setSrcHeight(final int srcHeight) {
		this.srcHeight = srcHeight;
	}
	
}
