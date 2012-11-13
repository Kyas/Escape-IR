package fr.escape.game.entity.weapons.shot;

import fr.escape.game.entity.Drawable;
import fr.escape.game.entity.Entity;
import fr.escape.game.entity.Moveable;
import fr.escape.game.message.Receiver;

//TODO Comment
public interface Shot extends Drawable, Moveable, Receiver, Entity {
	
	/**
	 * @see Shot#receive(int)
	 */
	public final static int MESSAGE_LOAD = 0;
	public final static int MESSAGE_FIRE = 1;
	public final static int MESSAGE_CRUISE = 2;
	public final static int MESSAGE_HIT = 3;
	public final static int MESSAGE_DESTROY = 4;
	
	/**
	 * <p>
	 * Shot have different state depending on the situation.
	 * 
	 * <p>
	 * If you need to change its state, use this method with the given protocol:
	 * 
	 * <ul>
	 * <li>MESSAGE_LOADED: Shot loaded in Ship.</li>
	 * <li>MESSAGE_FIRE: Shot just shoot from Ship.</li>
	 * <li>MESSAGE_CRUISE: Shot in cruise state.</li>
	 * <li>MESSAGE_HIT: Shot hit something.</li>
	 * <li>MESSAGE_DESTROY: Shot need to be destroyed.</li>
	 * </ul>
	 * 
	 * <b>By default:</b> MESSAGE_LOADED.
	 * 
	 * @see Receiver#receive(int)
	 */
	@Override
	public void receive(int message);
		
	public int getState();
	
	public void setFireMask(boolean isPlayer);
	
	public int getDamage();
}
