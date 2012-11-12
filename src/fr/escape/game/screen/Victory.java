package fr.escape.game.screen;

import java.awt.Color;
import java.awt.Font;

import fr.escape.app.Input;
import fr.escape.app.Screen;
import fr.escape.game.Escape;
import fr.escape.graphics.Texture;
import fr.escape.resources.font.FontLoader;
import fr.escape.resources.texture.TextureLoader;

public final class Victory implements Screen {

	private static final String TAG = Victory.class.getSimpleName();
	
	private static final String VICTORY_TITLE = "Victory !!!";
	private static final String VICTORY_CREDITS_TITLE = "Credits :";
	private static final String AUTHOR_TQ = "Thomas QUANTIN";
	private static final String AUTHOR_TL = "Thomas LE ROUX";
	
	private static final float FSIZE_H1 = 22.0f;
	private static final float FSIZE_H2 = 18.0f; 
	private static final float FSIZE_H3 = 14.0f; 
	private static final int CSPACE = 15; 
	
	private final Font fontH1;
	private final Font fontH2;
	private final Font fontH3;
	private final Escape game;
	private final Texture background;
	private final Texture user;
	
	public Victory(Escape game) {
		this.game = game;
		Font baseFont = game.getResources().getFont(FontLoader.VISITOR_ID);
		this.fontH1 = baseFont.deriveFont(FSIZE_H1);
		this.fontH2 = baseFont.deriveFont(FSIZE_H2);
		this.fontH3 = baseFont.deriveFont(FSIZE_H3);
		this.background = game.getResources().getTexture(TextureLoader.BACKGROUND_VICTORY);
		this.user = game.getResources().getTexture(TextureLoader.SHIP_SWING);
	}
	
	@Override
	public boolean touch(Input i) {
		game.getActivity().debug(TAG, "User click: Go on Menu Screen");
		game.setMenuScreen();
		return true;
	}

	@Override
	public boolean move(Input i) {
		return false;
	}

	@Override
	public void render(long delta) {
		
		game.getGraphics().draw(background, 0, 0, game.getGraphics().getWidth(), game.getGraphics().getHeight());
		
		int x = (game.getGraphics().getWidth() / 2);
		int y = (game.getGraphics().getHeight() / 4);
		
		drawString(VICTORY_TITLE, fontH1, y, x);
		
		int ux = (game.getGraphics().getWidth() / 2) - (user.getWidth() / 2);
		int uy = (game.getGraphics().getHeight() / 2) - (user.getWidth() / 2);
		
		game.getGraphics().draw(user, ux, uy);
		
		y = 3 * (game.getGraphics().getHeight() / 4);
		
		drawString(VICTORY_CREDITS_TITLE, fontH2, y, x);
		
		y = y + CSPACE + (int) (fontH3.getSize() * 1.3);
		
		drawString(AUTHOR_TL, fontH3, y, x);

		y = y + (int) (fontH3.getSize() * 1.3);
		drawString(AUTHOR_TQ, fontH3, y, x);
		
	}

	@Override
	public void show() {
		game.getOverlay().hide();
	}

	@Override
	public void hide() {}
	
	private void drawString(String message, Font font, int y, int x) {
		Screens.drawStringInCenterPosition(game.getGraphics(), message, x, y, font, Color.WHITE);
	}

}
