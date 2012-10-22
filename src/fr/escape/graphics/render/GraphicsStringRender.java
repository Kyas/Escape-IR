package fr.escape.graphics.render;

import java.awt.Color;
import java.awt.Font;
import java.util.Objects;

public class GraphicsStringRender extends GraphicsRender {

	private Color color;
	private Font font;
	private String message;
	private int x;
	private int y;
	
	public GraphicsStringRender() {
		recycle();
	}
	
	@Override
	protected void render() {
		
		Objects.requireNonNull(getColor());
		Objects.requireNonNull(getFont());
		Objects.requireNonNull(getMessage());

		getGraphics().setPaint(getColor());
		getGraphics().setFont(getFont());
		getGraphics().drawString(getMessage(), getX(), getY());
		
	}
	
	public GraphicsStringRender setX(final int x) {
		this.x = x;
		return this;
	}
	
	public GraphicsStringRender setY(final int y) {
		this.y = y;
		return this;
	}
	
	public GraphicsStringRender setColor(final Color color) {
		this.color = color;
		return this;
	}
	
	public GraphicsStringRender setFont(final Font font) {
		this.font = font;
		return this;
	}
	
	public GraphicsStringRender setMessage(final String message) {
		this.message = message;
		return this;
	}

	private int getX() {
		return x;
	}
	
	private int getY() {
		return y;
	}

	private String getMessage() {
		return message;
	}

	@Override
	public void recycle() {
		color = null;
		font = null;
		message = null;
		x = 0;
		y = 0;
	}
	
	private Color getColor() {
		return color;
	}

	private Font getFont() {
		return font;
	}
	
}
