package fr.escape.game.entity.ships;

import java.util.List;

import org.jbox2d.dynamics.Body;

import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.weapons.Weapon;
import fr.escape.graphics.AnimationTexture;

//TODO comment
public class RegularShip extends AbstractShip {
	
	RegularShip(Body body, List<Weapon> weapons, boolean isPlayer, EntityContainer container, AnimationTexture textures) {
		super(body, weapons, isPlayer, container, textures);
	}

}
