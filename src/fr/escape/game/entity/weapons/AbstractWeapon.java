package fr.escape.game.entity.weapons;

import java.util.Objects;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.weapons.shot.Shot;
import fr.escape.game.entity.weapons.shot.ShotFactory;
import fr.escape.graphics.Texture;

public abstract class AbstractWeapon implements Weapon {
	
	private final Texture drawable;
	private final EntityContainer container;
	private final ShotFactory factory;
	
	private int ammunition;
	private Shot shot;
	
	public AbstractWeapon(Texture texture, EntityContainer eContainer, ShotFactory sFactory, int defaultAmmunition) {
		drawable = Objects.requireNonNull(texture);
		container = Objects.requireNonNull(eContainer);
		factory = Objects.requireNonNull(sFactory);
		ammunition = defaultAmmunition;
	}

	@Override
	public Texture getDrawable() {
		return drawable;
	}

	public int getAmmunition() {
		return ammunition;
	}
	
	@Override
	public boolean isEmpty() {
		return getAmmunition() <= 0;
	}
	
	protected abstract Shot createShot(float x, float y);

	protected ShotFactory getFactory() {
		return factory;
	}
	
	@Override
	public boolean load(float x, float y) {
		if(!isEmpty() && shot == null) {
			
			shot = Objects.requireNonNull(createShot(x, y));
			shot.receive(Shot.MESSAGE_LOAD);
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean fire(float[] velocity) {

		if(shot != null) {
			
			// TODO
			shot.moveBy(velocity);
			
			container.push(shot);
			shot.receive(Shot.MESSAGE_FIRE);
			
			// TODO Apply Speed and Angle
			shot.receive(Shot.MESSAGE_CRUISE);
			
			shot = null;
			ammunition--;
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public Shot getShot() {
		return shot;
	}
	
	@Override
	public void update(Graphics graphics, long delta) {
		if(shot != null) {
			shot.update(graphics, delta);
		}
	}
	
	@Override
	public boolean unload() {
		if(shot != null) {
			shot.receive(Shot.MESSAGE_DESTROY);
			shot = null;
		}
		return false;
	}
	
}
