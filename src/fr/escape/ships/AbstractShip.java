package fr.escape.ships;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.dynamics.Body;

import fr.escape.weapons.Weapon;

public abstract class AbstractShip implements Ship {
	private final Body body;
	private final ArrayList<Weapon> weapons;
	private int activeWeapon;
	
	public AbstractShip(Body body) {
		this.weapons = new ArrayList<>(4);
		this.activeWeapon = 0;
		this.body = body;
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
}