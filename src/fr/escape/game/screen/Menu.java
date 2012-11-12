package fr.escape.game.screen;

import java.awt.Color;
import java.awt.Font;
import java.util.Objects;

import fr.escape.app.Input;
import fr.escape.app.Screen;
import fr.escape.game.Escape;
import fr.escape.graphics.Texture;
import fr.escape.resources.font.FontLoader;
import fr.escape.resources.texture.TextureLoader;

/**
 * <p>
 * 
 */
public final class Menu implements Screen {

	private static final String TITLE = "Escape";
	private static final String FOOTER = "Insert Coin";
	
	private final Font font;
	private final Escape game;
	private final Texture background;
	
	public Menu(Escape game) {
		this.game = Objects.requireNonNull(game);
		this.font = game.getResources().getFont(FontLoader.VISITOR_ID);
		this.background = game.getResources().getTexture(TextureLoader.BACKGROUND_MENU);
	}
	
	@Override
	public boolean touch(Input i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean move(Input i) {
		return false;
	}

	@Override
	public void render(long delta) {
		
		game.getGraphics().draw(background, 0, 0, game.getGraphics().getWidth(), game.getGraphics().getHeight());
		
		Screens.drawStringInCenterPosition(
				game.getGraphics(), TITLE, 
				(game.getGraphics().getWidth() / 2), 200, 
				font, Color.WHITE);
		
		Screens.drawStringInCenterPosition(
				game.getGraphics(), FOOTER, 
				(game.getGraphics().getWidth() / 2),
				game.getGraphics().getHeight() - 30, 
				font, Color.WHITE);
	}

	@Override
	public void show() {
		game.getOverlay().hide();
	}

	@Override
	public void hide() {}

}
