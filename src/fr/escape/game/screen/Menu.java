package fr.escape.game.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
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
	private static final int HEADER_MARGING = 200;
	private static final int FOOTER_MARGING = 30;
	private static final int SIDE_PADDING = 50;
	
	private final Font font;
	private final Escape game;
	private final Texture background;
	private final Texture grid;
	
	public Menu(Escape game) {
		this.game = Objects.requireNonNull(game);
		this.font = game.getResources().getFont(FontLoader.VISITOR_ID);
		this.background = game.getResources().getTexture(TextureLoader.BACKGROUND_MENU);
		this.grid = game.getResources().getTexture(TextureLoader.MENU_UI_GRID);
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
				(game.getGraphics().getWidth() / 2), HEADER_MARGING, 
				font, Color.WHITE);
		
		int y = HEADER_MARGING + font.getSize();
		int height = game.getGraphics().getHeight() - (FOOTER_MARGING * 3);
		
		Rectangle gridArea = new Rectangle(SIDE_PADDING, y, 
				game.getGraphics().getWidth() - (SIDE_PADDING * 2), 
				(game.getGraphics().getHeight() - (FOOTER_MARGING * 3)) - y);
		
		//game.getGraphics().draw(grid, SIDE_PADDING, y, game.getGraphics().getWidth() - SIDE_PADDING, height);
		game.getGraphics().draw(gridArea, Color.GRAY);
		
		Screens.drawStringInCenterPosition(
				game.getGraphics(), FOOTER, 
				(game.getGraphics().getWidth() / 2),
				game.getGraphics().getHeight() - FOOTER_MARGING, 
				font, Color.WHITE);
	}

	@Override
	public void show() {
		game.getOverlay().hide();
	}

	@Override
	public void hide() {}

}
