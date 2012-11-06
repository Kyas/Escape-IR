package fr.escape.game.entity.weapons.shot;

import fr.escape.game.entity.Drawable;
import fr.escape.game.entity.Entity;
import fr.escape.game.entity.Moveable;
import fr.escape.game.message.Receiver;

public interface Shot extends Drawable, Moveable, Receiver, Entity {
	
	/**
	 * <p>
	 * Shot have different state depending on the situation.
	 * 
	 * <p>
	 * If you need to change its state, use this method with the given protocol:
	 * 
	 * <ul>
	 * <li>0: Shot loaded in Ship</li>
	 * <li>1: Shot just shoot from Ship</li>
	 * <li>2: Shot in cruise</li>
	 * <li>3: Shot hit something</li>
	 * <li>4: Shot is destroyed</li>
	 * </ul>
	 * 
	 * <b>By default:</b> state is 0.
	 * 
	 * @see Receiver#receive(int)
	 */
	@Override
	public void receive(int message);
}
