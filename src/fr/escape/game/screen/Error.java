package fr.escape.game.screen;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import fr.escape.app.Screen;
import fr.escape.game.Escape;
import fr.escape.graphics.RepeatableScrollingTexture;

// TODO Finish
public class Error implements Screen {

	private final static String TAG = Error.class.getSimpleName();
	
	private final Escape game;
	private final Random random;
	private final List<String> message;

	private Font font;
	private RepeatableScrollingTexture background;
	
	private boolean fallbackBackground;
	private boolean fallbackFont;
	
	public Error(Escape game) {
		
		this.game = game;
		this.random = new Random(200);
		
		try {
			font = game.getResources().getFont("visitor");
			fallbackFont = false;
		} catch(NoSuchElementException e) {
			fallbackFont = true;
		}
		
		try {
			background = new RepeatableScrollingTexture(game.getResources().getDrawable("berror"));
			fallbackBackground = false;
		} catch(NoSuchElementException e) {
			fallbackBackground = true;
			game.getActivity().error(TAG, "Cannot create RepeatableScrollingTexture", e);
		}
		
		this.message = new ArrayList<String>();
		
		message.add("An error has occurred :");
		message.add("");
		message.add("Please restart your Game");
		message.add("and/or Contact Support");
		
	}
	
	@Override
	public void render(long delta) {
		
		Color color = Color.WHITE;
		
		if(!fallbackBackground) {

			background.setXPercent(random.nextFloat());
			background.setYPercent(random.nextFloat());
			
			game.getGraphics().draw(background, 0, 0, game.getGraphics().getWidth(), game.getGraphics().getHeight());
			
		} else {
			color = Color.BLACK;
		}
		
		if(!fallbackFont) {
			
			int space = 20;
			int y = (game.getGraphics().getHeight() / 2) - (message.size() / 2 * space);
			int x = 20;

			for(String info: message) {
				game.getGraphics().draw(info, x, y, font, color);
				y += space;
			}
			
		} else {
			
			int y = 25;
			int x = 20;
			int space = 20;
			
			for(String info: message) {
				game.getGraphics().draw(info, x, y, color);
				y += space;
			}
			
		}

	}

	@Override
	public void show() {
		game.hideAll();
	}

	@Override
	public void hide() {}

}
