package fr.escape.game.entity.ships;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.dynamics.Body;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;
import fr.escape.game.entity.weapons.Weapon;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public abstract class AbstractShip implements Ship {
	
	private final Body body;
	private final List<Weapon> weapons;
	private final boolean isPlayer;
	private int activeWeapon;
	
	private final EdgeNotifier eNotifier;
	private final KillNotifier kNotifier;
	
	private final Texture coreShip;
	
	public AbstractShip(Body body, List<Weapon> weapons, boolean isPlayer,EdgeNotifier eNotifier,KillNotifier kNotifier) {
		this.weapons = weapons;
		this.activeWeapon = 0;
		this.body = body;
		this.isPlayer = isPlayer;
		this.eNotifier = eNotifier;
		this.kNotifier = kNotifier;
		
		this.coreShip = Foundation.RESOURCES.getTexture(TextureLoader.DEBUG_WIN);
	}
	
	public boolean isPlayer() {
		return isPlayer;
	}
	
	@Override
	public Weapon getActiveWeapon() {
		return weapons.get(activeWeapon);
	}
	
	@Override
	public List<Weapon> getAllWeapons() {
		return weapons;
	}
	
	@Override
	public void setActiveWeapon(int which) {
		if(which < 0 || which >= weapons.size()) {
			throw new IndexOutOfBoundsException();
		}
		this.activeWeapon = which;
	}
	
	@Override
	public Body getBody() {
		return body;
	}
	
	public int getRadius() {
		return coreShip.getHeight() / 2;
	}
	
	@Override
	public float getX() {
		return body.getPosition().x;
	}
	
	@Override
	public float getY() {
		return body.getPosition().y;
	}
	
	@Override
	public void draw(Graphics graphics) {
		int x = CoordinateConverter.toPixel(body.getPosition().x) - coreShip.getWidth() / 2;
		int y = CoordinateConverter.toPixel(body.getPosition().y) - coreShip.getHeight() / 2;
		graphics.draw(coreShip, x, y);
		getActiveWeapon().draw(graphics);
	}
	
	@Override
	public void update(Graphics graphics, long delta) {
		draw(graphics);
	}
}