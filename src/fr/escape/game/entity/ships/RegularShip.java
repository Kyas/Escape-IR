package fr.escape.game.entity.ships;

import java.util.List;

import org.jbox2d.dynamics.Body;

import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;
import fr.escape.game.entity.weapons.Weapon;
import fr.escape.graphics.AnimationTexture;

//TODO comment
public class RegularShip extends AbstractShip {
	
	RegularShip(Body body, List<Weapon> weapons, boolean isPlayer,
			EdgeNotifier eNotifier, KillNotifier kNotifier,
			AnimationTexture textures) {
		
		super(body, weapons, isPlayer, eNotifier, kNotifier, textures);
	}
	
	@Override
	public void rotateBy(int angle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRotation(int angle) {
		// TODO Auto-generated method stub
		
	}

}
