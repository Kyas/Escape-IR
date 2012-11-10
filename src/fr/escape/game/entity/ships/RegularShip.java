package fr.escape.game.entity.ships;

import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import fr.escape.app.Graphics;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;
import fr.escape.game.entity.weapons.Weapon;
import fr.escape.game.entity.weapons.shot.Shot;

//TODO comment
public class RegularShip extends AbstractShip {
	
	public RegularShip(Body body, List<Weapon> weapons,boolean isPlayer,EdgeNotifier edgeNotifier,KillNotifier killNotifier) {
		super(body,weapons,isPlayer,edgeNotifier,killNotifier);
	}
	
	@Override
	public void setPosition(World world,Graphics graphics,float[] velocity) {

		Body body = getBody();
		
		if(body.isActive()) {
			
			Shot shot = getActiveWeapon().getShot();
			
			int x = CoordinateConverter.toPixelX(body.getPosition().x);
			int y = CoordinateConverter.toPixelY(body.getPosition().y);
			int radius = getRadius();
			
			if(isPlayer() && (x <= radius || x >= graphics.getWidth() - radius || y <= (graphics.getHeight() * 2) / 3 + radius || y >= graphics.getHeight() - radius)) {
				//System.out.println(x + " " + y);
				velocity[0] = 0.1f;
				velocity[1] *= -1;
				velocity[2] *= -1;
			}
			
			float[] tmp = velocity;
			
			/*if(isPlayer()) {
				System.out.println(velocity[1] + " " + velocity[2]);
				System.out.println(velocity[0]);
			}*/

			if(velocity[0] > 0) {
				body.setLinearVelocity(new Vec2(velocity[1],velocity[2]));
				velocity[0] -= Math.abs(Math.max(velocity[1],velocity[2]));
				
			} else {
				body.setLinearVelocity(new Vec2(0,0));
			}
			
			if(shot != null) {
				shot.setPosition(world,graphics,tmp);
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
