package fr.escape.game.screen;

import java.awt.Color;
import java.awt.Font;

import fr.escape.app.Foundation;
import fr.escape.app.Input;
import fr.escape.app.Screen;
import fr.escape.game.Escape;
import fr.escape.graphics.Texture;
import fr.escape.resources.font.FontLoader;
import fr.escape.resources.texture.TextureLoader;

/**
 * <p>
 * A screen that display "Victory !!!" and Credits.
 * 
 */
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
	private final Runnable confirm;
	
	public Victory(Escape game) {
		this.game = game;
		Font baseFont = game.getResources().getFont(FontLoader.VISITOR_ID);
		this.fontH1 = baseFont.deriveFont(FSIZE_H1);
		this.fontH2 = baseFont.deriveFont(FSIZE_H2);
		this.fontH3 = baseFont.deriveFont(FSIZE_H3);
		this.background = game.getResources().getTexture(TextureLoader.BACKGROUND_VICTORY);
		this.user = game.getResources().getTexture(TextureLoader.SHIP_SWING);
		this.confirm = new Runnable() {
			
			@Override
			public void run() {
				next();
			}
			
		};
	}
	
	@Override
	public boolean touch(Input i) {
		Foundation.ACTIVITY.log(TAG, "User Launch: MENU_SCREEN");
		Foundation.ACTIVITY.post(confirm);
		return true;
	}

	@Override
	public boolean move(Input i) {
		return touch(i);
	}

	@Override
	public void render(long delta) {
		
		game.getGraphics().draw(background, 0, 0, game.getGraphics().getWidth(), game.getGraphics().getHeight());
	
		int x = (game.getGraphics().getWidth() / 2);
		int y = (game.getGraphics().getHeight() / 4);
		
		Screens.drawStringInCenterPosition(game.getGraphics(), VICTORY_TITLE, x, y, fontH1, Color.WHITE);
		
		int ux = (game.getGraphics().getWidth() / 2) - (user.getWidth() / 2);
		int uy = (game.getGraphics().getHeight() / 2) - (user.getWidth() / 2);
		
		game.getGraphics().draw(user, ux, uy);
		
		y = 3 * (game.getGraphics().getHeight() / 4);
		
		Screens.drawStringInCenterPosition(game.getGraphics(), VICTORY_CREDITS_TITLE, x, y, fontH2, Color.WHITE);
		
		y = y + CSPACE + (int) (fontH3.getSize() * 1.3);
		
		Screens.drawStringInCenterPosition(game.getGraphics(), AUTHOR_TL, x, y, fontH3, Color.WHITE);

		y = y + (int) (fontH3.getSize() * 1.3);
		
		Screens.drawStringInCenterPosition(game.getGraphics(), AUTHOR_TQ, x, y, fontH3, Color.WHITE);
		
	}

	@Override
	public void show() {
		game.getOverlay().hide();
	}

	@Override
	public void hide() {}

	/**
	 * Launch Menu when the User touch the Screen.
	 */
	public void next() {
		game.setMenuScreen();
	}
	
}
