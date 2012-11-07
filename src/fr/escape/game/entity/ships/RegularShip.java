package fr.escape.game.entity.ships;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;

//TODO comment
public class RegularShip extends AbstractShip {
	
	public RegularShip(Body body,int life,boolean isPlayer) {
		super(body,life,isPlayer);
	}
	
	@Override
	public void setPosition(World world,Graphics graphics,float[] velocity) {
		if(!isDestroyed()) {
			int coeff = Math.max(Foundation.graphics.getHeight(),Foundation.graphics.getWidth());
			Body body = getBody();
			int x = (int) ((body.getPosition().x * coeff) / 10);
			int y = (int) ((body.getPosition().y * coeff) / 10);
			if(velocity[0] > 0) {
				body.setLinearVelocity(new Vec2(velocity[1],velocity[2]));
				velocity[0] -= Math.abs(Math.max(velocity[1],velocity[2]));
			} else {
				body.setLinearVelocity(new Vec2(0,0));
			}
			draw(graphics);
			world.step(1.0f/60.0f,6,2);
		}
	}

	@Override
	public void moveBy(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rotateBy(int angle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosition(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRotation(int angle) {
		// TODO Auto-generated method stub
		
	}

}
