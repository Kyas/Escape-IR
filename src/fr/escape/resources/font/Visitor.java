package fr.escape.resources.font;

import java.awt.Font;

public final class Visitor extends FontLoader {

	private Font font;
	
	@Override
	public Font load() throws Exception {
		
		if(font == null) {
			font = Font.createFont(Font.TRUETYPE_FONT, getPath().resolve("visitor.ttf").toFile());
			font = font.deriveFont(18.f);
		}
		
		return font;
	}
}
