package fr.escape.game.entity.ships;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.dynamics.Body;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.weapons.Weapon;
import fr.escape.resources.texture.TextureLoader;

public abstract class AbstractShip implements Ship {
	private final Body body;
	private final ArrayList<Weapon> weapons;
	private final boolean isPlayer;
	private int activeWeapon;
	private int life;
	
	public AbstractShip(Body body,int life,boolean isPlayer) {
		this.weapons = new ArrayList<>(4);
		this.activeWeapon = 0;
		this.body = body;
		this.life = life;
		this.isPlayer = isPlayer;
	}
	
	@Override
	public boolean isDestroyed() {
		return life <= 0;
	}
	
	public boolean isPlayer() {
		return isPlayer;
	}

	@Override
	public void damage(int taken) {
		life -= taken;
		if(life <= 0) {
			body.setActive(false);
		}
		System.out.println("Life : " + life);
	}
	
	@Override
	public int getActiveWeapon() {
		return activeWeapon;
	}
	
	@Override
	public List<Weapon> getAllWeapons() {
		return weapons;
	}
	
	@Override
	public void setActiveWeapon(int which) {
		if(which < 0 || which >= weapons.size()) throw new IllegalArgumentException("Out of bound : index unknown");
		this.activeWeapon = which;
	}
	
	@Override
	public Body getBody() {
		return body;
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
		int coeff = Math.max(Foundation.graphics.getHeight(),Foundation.graphics.getWidth());
		int x = (int)(body.getPosition().x / 10 * coeff);
		int y = (int)(body.getPosition().y  / 10 * coeff);
		graphics.draw(Foundation.resources.getTexture(TextureLoader.DEBUG_WIN),x,y);
	}
	
	@Override
	public void update(Graphics graphics, long delta) {
		draw(graphics);
	}
}