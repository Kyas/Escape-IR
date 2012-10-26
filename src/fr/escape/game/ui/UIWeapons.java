package fr.escape.game.ui;

import java.util.LinkedList;
import java.util.List;

import fr.escape.app.Game;
import fr.escape.app.Input;
import fr.escape.game.WeaponsUpdater.WeaponsListener;
import fr.escape.graphics.Texture;
import fr.escape.weapons.Weapon;

public class UIWeapons extends AbstractOverlay {
	
	private final static int TOP_MARGING = 3;
	private final static int BOTTOM_MARGING = 3;
	private final static int LEFT_MARGING = 3;
	private final static int RIGHT_MARGING = 3;
	
	private final Game game;
	private final Texture background;
	private final List<Weapon> weapons;
	private final List<WeaponsListener> listeners;
	
	private int x;
	private int y;
	private int width;
	private int height;

	public UIWeapons(Game game, LinkedList<Weapon> weapons) {
		
		this.game = game;
		this.background = game.getResources().getDrawable("bui");
		this.weapons = weapons;
		this.listeners = new LinkedList<>();
		
		this.width = game.getGraphics().getWidth();
		this.y = (int) (((double) 1 / 5) * game.getGraphics().getHeight());
		
		this.x = 0;
//		this.x = game.getGraphics().getWidth() - (wea1.getWidth() + 6);
//		this.y = 20;
//		this.width = game.getGraphics().getWidth();
//		this.height = y + wea1.getHeight() + 6;

	}

	@Override
	public void render(long delta) {
		game.getGraphics().draw(background, x, y, width, height);
		//game.getGraphics().draw(wea1, x + 3, y + 3);
	}

	@Override
	public boolean contains(Input touch) {
		return false;
	}
}
