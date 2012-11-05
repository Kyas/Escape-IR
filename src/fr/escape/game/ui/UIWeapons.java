package fr.escape.game.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import fr.escape.app.Game;
import fr.escape.app.Input;
import fr.escape.game.entity.weapons.Weapon;
import fr.escape.game.entity.weapons.Weapons;
import fr.escape.game.message.Receiver;
import fr.escape.game.message.Sender;
import fr.escape.graphics.Texture;
import fr.escape.resources.font.FontLoader;

public class UIWeapons extends AbstractOverlay implements Sender {
	
	private final static int TOP_MARGING = 3;
	private final static int BOTTOM_MARGING = 3;
	private final static int LEFT_MARGING = 5;
	private final static int RIGHT_MARGING = 3;
	private final static float FONT_SIZE = 10.0f;
	private final static Color FONT_COLOR = Color.WHITE;
	
	private final Game game;
	//private final Texture background;
	private final Font font;
	private final List<Weapon> weapons;
	private final List<Rectangle> touchArea;
	private final Receiver receiver;
	
	private int x;
	private int y;
	private int width;
	private int height;

	public UIWeapons(Game game, Receiver receiver, List<Weapon> weapons) {
		
		Objects.requireNonNull(game);
		Objects.requireNonNull(weapons);
		Objects.requireNonNull(receiver);
		
		this.game = game;
		//this.background = game.getResources().getDrawable("bui");
		this.font = game.getResources().getFont(FontLoader.VISITOR_ID).deriveFont(FONT_SIZE);
		this.weapons = weapons;
		this.receiver = receiver;
		
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
		//this.game.getGraphics().draw(this.background, this.x, this.y, this.width, this.height);
		
		int offset = this.y;
		for(Weapon w : weapons) {
			
			offset += TOP_MARGING;
			
			renderWeaponDrawable(w.getDrawable(), this.x + LEFT_MARGING, offset);
			renderWeaponAmmunition(String.valueOf(w.getAmmunition()), this.x + LEFT_MARGING, (offset + Weapons.getDrawableHeight()), LEFT_MARGING);
			
			offset += (Weapons.getDrawableHeight() + BOTTOM_MARGING);
		}
	}
	
	/**
	 * <p>
	 * Send the Weapons ID to the Receiver.
	 * 
	 * @param weaponsID Weapon ID
	 */
	@Override
	public void send(int weaponsID) {
		receiver.receive(weaponsID);
	}

	@Override
	public boolean touch(Input touch) {
		
		int i = 0;
		
		for(Rectangle rectangle : touchArea) {
			if(rectangle.contains(touch.getX(), touch.getY())) {
				send(i);
				return true;
			}
			i++;
		}
		
		return false;
	}

	@Override
	public void register(Receiver receiver) {}
	
	private void renderWeaponDrawable(Texture weapon, int x, int y) {
		game.getGraphics().draw(weapon, x, y);
	}
	
	private void renderWeaponAmmunition(String ammunition, int x, int y, int offset) {
		game.getGraphics().draw(ammunition, x + offset, y - offset, font, FONT_COLOR);
	}
}
