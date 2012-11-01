package fr.escape.ships;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import fr.escape.app.Graphics;

//TODO comment
public class RegularShip extends AbstractShip {
	
	public RegularShip(Body body) {
		super(body);
	}
	
	@Override
	public void setPosition(World world,Graphics graphics,float[] val) {
		getBody().setLinearVelocity(new Vec2(val[0],val[1]));
		draw(graphics);
		world.step(1.0f/60.0f,6,2);
	}

}
