package fr.escape.graphics;

import java.awt.Graphics2D;
import java.util.NoSuchElementException;

public final class AnimationTexture implements TextureOperator {

	private final Texture[] textures;
	
	private int index;
	private int width;
	private int height;
	
	private boolean reverse;
	
	public AnimationTexture(Texture... textures) {
		this.textures = textures;
		this.index = 0;
		this.width = 0;
		this.height = 0;
		this.reverse = false;
	}
	
	public boolean hasNext() {
		return index < textures.length | index >= 0;
	}
	
	public void rewind() {
		this.index = 0;
	}
	
	public void reverse() {
		reverse = true;
	}
	
	public void forward() {
		reverse = false;
	}
	
	public void next() {
		
		if(!hasNext()) {
			throw new NoSuchElementException();
		}
				
		if(reverse) {
			index--;
			if(index < 0) index += textures.length;
		} else {
			index = (index + 1) % textures.length;
		}
		
	}
	
	public int getWidth() {
		
		if(width == 0) {
			for(Texture texture: textures) {
				width = Math.max(width, texture.getWidth());
			}
		}
		
		return width;
	}
	
	public int getHeight() {
		
		if(height == 0) {
			for(Texture texture: textures) {
				height = Math.max(height, texture.getHeight());
			}
		}
		
		return height;
	}
	
	@Override
	public void draw(Graphics2D graphics, int x, int y, int width, int height, double angle) {
	
		System.out.println("Index : " + index);
		Texture texture = textures[index];
		texture.draw(graphics, x, y, width, height, 0, 0, texture.getWidth(), texture.getHeight(), angle);
		
	}

}
