package fr.escape.game.entity.ships;

import java.util.List;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.weapons.Weapon;
import fr.escape.graphics.AnimationTexture;

//TODO comment
public class RegularShip extends AbstractShip {
	
	RegularShip(BodyDef bodyDef, FixtureDef fixture, List<Weapon> weapons, boolean isPlayer, int life, EntityContainer container, AnimationTexture textures) {
		super(bodyDef, fixture, weapons, isPlayer, life, container, textures);
	}

}
