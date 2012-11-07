package fr.escape.game.entity.ships;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import fr.escape.app.Graphics;
import fr.escape.game.entity.CoordinateConverter;

//TODO comment
public class RegularShip extends AbstractShip {
	
	public RegularShip(Body body,boolean isPlayer) {
		super(body,isPlayer);
	}
	
	@Override
	public void setPosition(World world,Graphics graphics,float[] velocity) {

		Body body = getBody();
		
		if(body.isActive()) {
			
			int x = CoordinateConverter.toPixel(body.getPosition().x);
			int y = CoordinateConverter.toPixel(body.getPosition().y);
			
			if(isPlayer() && (x <= 0 || x >= graphics.getWidth() - 50 || y <= 0 || y >= graphics.getHeight() - 60)) {
				System.out.println(x + " " + y);
				velocity[0] = 0.1f;
				velocity[1] *= -1;
				velocity[2] *= -1;
			}
			
			if(isPlayer()) {
				System.out.println(velocity[1] + " " + velocity[2]);
				System.out.println(velocity[0]);
			}

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
