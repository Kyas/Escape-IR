package fr.escape.game.entity.weapons.shot;

import java.awt.Rectangle;
import java.util.Objects;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import fr.escape.app.Graphics;
import fr.escape.game.entity.Entity;
import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;

public abstract class AbstractShot implements Shot {
	
	private final EdgeNotifier eNotifier;
	private final KillNotifier kNotifier;
	private final Body body;
	
	private int x;
	private int y;
	private int angle;
	
	public AbstractShot(Body body, EdgeNotifier edgeNotifier, KillNotifier killNotifier) {
		
		this.body = body;
		this.eNotifier = Objects.requireNonNull(edgeNotifier);
		this.kNotifier = Objects.requireNonNull(killNotifier);
		
		this.x = 0;
		this.y = 0;
		this.angle = 0;
	}
	
	@Override
	public void moveBy(int x, int y) {
		this.setPosition(this.x + x, this.y + y);
	}

	@Override
	public void rotateBy(int angle) {
		this.setRotation(this.angle + angle);
	}
	
	@Override
	public void setPosition(float x, float y) {
		
		/*this.x = x;
		this.y = y;
		
		if(!eNotifier.isInside(getEdge())) {
			eNotifier.edgeReached(this);
		}*/
	}
	
	public void setPosition(World world, Graphics graphics, float[] velocity) {
		if(body.isActive()) {
			if(velocity[0] > 0) {
				body.setLinearVelocity(new Vec2(velocity[1], velocity[2]));
				velocity[0] -= Math.abs(Math.max(velocity[1], velocity[2]));
			} else {
				body.setLinearVelocity(new Vec2(0, 0));
			}
			draw(graphics);
			Objects.requireNonNull(world).step(1.0f/60.0f,6 , 2);
		}
	}
	
	@Override
	public void setRotation(int angle) {
		angle = angle % 360;
		this.angle = angle;
	}
	
	
//	@Override
//	public void draw(Graphics graphics) {
//		graphics.draw(drawable, (getX() - (drawable.getWidth() / 2)), (getY() - (drawable.getHeight() / 2)), getAngle());
//	}
	
	@Override
	public Body getBody() {
		return body;
	}
	
	protected int getX() {
		return this.x;
	}
	
	protected int getY() {
		return this.y;
	}
	
	protected int getAngle() {
		return this.angle;
	}
	
	protected void destroy() {
		kNotifier.destroy(this);
	}
	
	@Override
	public void toDestroy() {
		kNotifier.toDestroy(this);		
	}
	
	protected abstract Rectangle getEdge(); 
	
}
