package fr.escape.game.entity.ships;

import java.util.List;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import fr.escape.game.entity.Drawable;
import fr.escape.game.entity.Entity;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.Moveable;
import fr.escape.game.entity.weapons.Weapon;
import fr.escape.game.message.Receiver;

// TODO Comment
public interface Ship extends Moveable, Drawable, Entity, Receiver {
	
	public static final int MESSAGE_EXECUTE_LEFT_LOOP = 0;
	public static final int MESSAGE_EXECUTE_RIGHT_LOOP = MESSAGE_EXECUTE_LEFT_LOOP + 1;
		
	public List<Weapon> getAllWeapons();
	
	public void setActiveWeapon(int which);
	
	public Weapon getActiveWeapon();
	
	public Body getBody();
	
	public float getX();
	
	public float getY();
	
	public boolean isPlayer();
	
	public boolean isWeaponLoaded();
	
	public boolean loadWeapon(World w, EntityContainer ec);
	
	public boolean fireWeapon(World world, EntityContainer ec, float[] velocity);
	
	/**
	 * <p>
	 * Send an action to the Ship.
	 * 
	 * <p>
	 * If you need to execute an action, use this method with the given protocol:
	 * 
	 * <ul>
	 * <li>MESSAGE_EXECUTE_LEFT_LOOP: Execute a Left Loop to the Ship.</li>
	 * <li>MESSAGE_EXECUTE_RIGHT_LOOP: Execute a Right Loop to the Ship.</li>
	 * </ul>
	 * 
	 * @see Receiver#receive(int)
	 */
	@Override
	public void receive(int message);

}
