package fr.escape.game.ui;

import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import fr.escape.app.Game;
import fr.escape.app.Input;
import fr.escape.game.WeaponsUpdater.WeaponsListener;
import fr.escape.graphics.Texture;
import fr.escape.weapons.Weapon;
import fr.escape.weapons.Weapons;

public class UIWeapons extends AbstractOverlay {
	
	private final static int TOP_MARGING = 3;
	private final static int BOTTOM_MARGING = 3;
	private final static int LEFT_MARGING = 5;
	private final static int RIGHT_MARGING = 3;
	
	private final Game game;
	private final Texture background;
	private final List<Weapon> weapons;
	private final List<WeaponsListener> listeners;
	private final List<Rectangle> touchArea;
	
	private int x;
	private int y;
	private int width;
	private int height;

	public UIWeapons(Game game, List<Weapon> weapons) {
		
		this.game = game;
		this.background = game.getResources().getDrawable("bui");
		this.weapons = weapons;
		this.listeners = new LinkedList<>();
		
		this.width = game.getGraphics().getWidth();
		this.y = (int) (((double) 1 / 16) * game.getGraphics().getHeight());
		
		this.x = this.width - (Weapons.getDrawableWidth() + LEFT_MARGING + RIGHT_MARGING);
		this.height = this.y + (this.weapons.size() * (Weapons.getDrawableHeight() + TOP_MARGING + BOTTOM_MARGING));
		
		this.touchArea = new LinkedList<>();
		
		int offset = this.y;
		for(int i = 0; i < weapons.size(); i++) {
			
			Rectangle r = new Rectangle(
					this.x + LEFT_MARGING, 
					offset + TOP_MARGING,
					Weapons.getDrawableWidth() + RIGHT_MARGING,
					Weapons.getDrawableHeight() + BOTTOM_MARGING
			);
			
			offset += TOP_MARGING + Weapons.getDrawableHeight() + BOTTOM_MARGING;
			touchArea.add(r);
			
		}
	}

	@Override
	public void render(long delta) {
		this.game.getGraphics().draw(this.background, this.x, this.y, this.width, this.height);
		
		int offset = this.y;
		for(Weapon w : weapons) {
			offset += TOP_MARGING;
			this.game.getGraphics().draw(w.getDrawable(), this.x + LEFT_MARGING, offset);
			offset += (Weapons.getDrawableHeight() + BOTTOM_MARGING);
		}
	}
	
	public void add(WeaponsListener listener) {
		listeners.add(listener);
	}

	@Override
	public boolean touch(Input touch) {
		
		int i = 0;
		
		for(Rectangle rectangle : touchArea) {
			if(rectangle.contains(touch.getX(), touch.getY())) {
				for(WeaponsListener listener : listeners) {
					listener.select(i);
				}
				return true;
			}
			i++;
		}
		
		return false;
	}
}
