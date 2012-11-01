package fr.escape.ships;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;

//TODO comment
public class RegularShip extends AbstractShip {
	
	public RegularShip(Body body) {
		super(body);
	}
	
	@Override
	public void setPosition(World world,Graphics graphics,int coeff) {
		Body body = getBody();
		int x = (int)(body.getPosition().x / 10 * coeff);
		int y = (int)(body.getPosition().y  / 10 * coeff);
		System.out.println(body.getPosition().x + " - " + body.getPosition().y);
		graphics.draw(Foundation.resources.getDrawable("wfireball"),x,y);
		getBody().setLinearVelocity(new Vec2(0.0f,0.5f));
		world.step(1.0f/60.0f,6,2);
		//getBody().applyLinearImpulse(impulse, point);
		//Foundation.graphics.draw(Foundation.resources.getDrawable("wfireball"),x,y);
	}

}
